package com.yyzm.shrinkcategoryrecycleview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 *@作者 11587
 *@描述 shrinkcategoryAdapter 自动分类折叠适配器
 *@创建时间 2019/2/28 22:49
 *@反射小知识：
 * getFields() 获取所有public字段,包括父类字段
 * getDeclaredFields()	获取所有字段,public和protected和private,但是不包括父类字段
 */
public class shrinkcategoryAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private static final int VIEW_TYPE_TITLE = 1;
    private static final int VIEW_TYPE_ITEM = 2;
    int ColumnNum;
    List<itemModel> mData;

    private int itemLayoutId;
    private int itemtvId;
    private int itembtnId;
    private int itemId;

    private int titleLayoutId;
    private int titletvId;
    private int titlebtnId;
    private int visiblenum;//合拢后显示的个数，默认等于行个数，表示只显示一行
    List<itemModel> ncData;//不可改变的数据

    private setOnItemClicklistener listener;


    public interface setOnItemClicklistener {
        void onItemListener(int position, View v, itemModel itemmodel);
    }

    public shrinkcategoryAdapter setOnclicklistener(setOnItemClicklistener listener) {
        this.listener = listener;
        return this;
    }

    //设置你自己的子布局的Id
    public shrinkcategoryAdapter setItemView(int childLayoutId, int childtvId, int childbtnId, int childId) {
        this.itemLayoutId = childLayoutId;
        this.itemtvId = childtvId;
        this.itembtnId = childbtnId;
        this.itemId = childId;
        return this;
    }

    //设置你自己的标签布局的Id
    public shrinkcategoryAdapter setTitleView(int titleLayoutId, int titletvId, int titlebtnId) {
        this.titleLayoutId = titleLayoutId;
        this.titletvId = titletvId;
        this.titlebtnId = titlebtnId;

        return this;
    }

    public shrinkcategoryAdapter(Context mContext, List<T> mData) {
        this.mContext = mContext;
        reSetData(mData);
    }

    public shrinkcategoryAdapter setColumNum(int columnNum) {
        this.ColumnNum = columnNum;
        return this;
    }


    public shrinkcategoryAdapter setVisiblenum(int visiblenum) {
        this.visiblenum = visiblenum;
        return this;
    }

    private boolean visitTitle = true;//是否存在title标签(不存在的话就全部放在一起)

    private void reSetData(List<T> clist) {
        String lastTag = "";
        this.mData = new ArrayList<>();
        for (int i = 0; i < clist.size(); i++) {
            T model = clist.get(i);
            String a = model.getClass().toString();
            String b[] = a.split(" ");
            Class<?> cla = null;
            try {
                cla = Class.forName(b[1]);
                Field mStudentField = cla.getField("word");
                String word = (String) mStudentField.get(model);
                mStudentField = cla.getField("tag");
                String tag = (String) mStudentField.get(model);
                mStudentField = cla.getField("drawableid");
                int drawableid = (Integer) mStudentField.get(model);
                mStudentField = cla.getField("singleline");
                int singleline = (Integer) mStudentField.get(model);
                itemModel cm = new itemModel();
                if (!tag.equals(lastTag) && visitTitle) {
//                    根据数据自动添加title
                    cm.setWord(tag);
                    cm.setSingleline(1);
                } else {
                    cm.setWord(word);
                    cm.setTag(tag);
                    cm.setDrawableid(drawableid);
                    cm.setSingleline(singleline);
                }
                lastTag = tag;
                this.mData.add(cm);
                this.ncData = mData;
            } catch (Exception e) {
                Toast.makeText(mContext, "请使用本View的bean字段！", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getSingleline() == 1) {
            return VIEW_TYPE_TITLE;
        }
        return VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ViewHolder vh = null;
        mInflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case VIEW_TYPE_TITLE:
                if (titleLayoutId != 0) {
                    vh = new newtitleHolder(mInflater.inflate(titleLayoutId, viewGroup, false));
                } else {
                    vh = new titleHolder(mInflater.inflate(R.layout.title, viewGroup, false));
                }
                break;
            case VIEW_TYPE_ITEM:
                if (itemLayoutId != 0) {
                    vh = new newitemHolder(mInflater.inflate(itemLayoutId, viewGroup, false));
                } else {
                    vh = new itemHolder(mInflater.inflate(R.layout.itme, viewGroup, false));
                }
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            onBindViewHolder(holder, position);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int i) {
        if (mData.get(i).getSingleline() == 1) {
            ((ViewHolder) viewHolder).tit_tv.setText(mData.get(i).getWord());
            ((ViewHolder) viewHolder).tit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //计算到底可移除几个
                    int index = 0;
                    if (((Button) v).getText().toString().equals("合拢")) {
                        List<itemModel> imData = new ArrayList<>();
                        imData.addAll(mData);
                        //当前点击的title名称
                        String name = ((ViewHolder) viewHolder).tit_tv.getText().toString();
                        while ((i + visiblenum + 1) < imData.size() &&
                                (imData.get(i + visiblenum + 1).getTag() == null ? "" : imData.get(i + visiblenum + 1).getTag()).equals(name)) {
                            imData.remove(i + visiblenum + 1);
                            index++;
                        }
                        mData = imData;
                        ((ViewHolder) viewHolder).tit_btn.setText("展开");
                        notifyDataSetChanged();
                    } else {
                        //用来存储获取到的数据（需要补充的数据）
                        List<itemModel> imData = new ArrayList<>();
                        //复制备份list用来模拟合拢删除数据，并获取

                        int posi = 1;
                        String name = ((ViewHolder) viewHolder).tit_tv.getText().toString();
                        //利用当前点击的title名称获取此item在原list中的位置
                        //原list中的位置
                        int oindex = 0;
                        for (int j = 0; j < ncData.size(); j++) {
                            itemModel ncmmodel = ncData.get(j);
                            if (ncmmodel.getSingleline() == 1 && ncmmodel.getWord().equals(name)) {
                                break;
                            }
                            oindex++;
                        }
                        while ((oindex + visiblenum + posi) < ncData.size() &&
                                (ncData.get(oindex + visiblenum + posi).getTag() == null ? "" : ncData.get(oindex + visiblenum + posi).getTag()).equals(name)) {
                            itemModel ncmodel = ncData.get(oindex + visiblenum + posi);
                            imData.add(ncmodel);
                            posi++;
                        }
                        mData.addAll((i + visiblenum + 1), imData);
                        ((ViewHolder) viewHolder).tit_btn.setText("合拢");
                        notifyDataSetChanged();
                    }
                }
            });
        } else {
            ((ViewHolder) viewHolder).item_tv.setText(mData.get(i).getWord());
            ((ViewHolder) viewHolder).item_btn.setBackgroundResource(mData.get(i).getDrawableid());
            ((ViewHolder) viewHolder).item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int oindex = 0;
                    for (int j = 0; j < ncData.size(); j++) {
                        itemModel ncmmodel = ncData.get(j);
                        if (ncmmodel == mData.get(i)) {
                            break;
                        }
                        oindex++;
                    }
                    listener.onItemListener(oindex, v, mData.get(i));
                }
            });
            ((ViewHolder) viewHolder).item_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int oindex = 0;
                    for (int j = 0; j < ncData.size(); j++) {
                        itemModel ncmmodel = ncData.get(j);
                        if (ncmmodel == mData.get(i)) {
                            break;
                        }
                        oindex++;
                    }
                    listener.onItemListener(oindex, v, mData.get(i));
                }
            });
        }
    }

    /**
     * 设置recycle view的行个数
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        //解释一下这个的意思，通俗来说就是：当数据应为单行时则应该跨（你设置好的列数），否则单行显示，每个子view占一列
        GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (mData.get(i).getSingleline() == 2) {
                    return 1;
                } else {
                    return ColumnNum;
                }
            }
        });
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tit_tv;
        public TextView item_tv;
        public Button tit_btn;
        public Button item_btn;
        public LinearLayout title;
        public LinearLayout item;

        public ViewHolder(@NonNull View viewholder) {
            super(viewholder);
        }
    }

    public class titleHolder extends ViewHolder {
        public titleHolder(@NonNull View titleView) {
            super(titleView);
            tit_tv = titleView.findViewById(R.id.title_tv);
            tit_btn = titleView.findViewById(R.id.title_btn);
            title = titleView.findViewById(R.id.title_layout);
        }
    }

    public class itemHolder extends ViewHolder {
        public itemHolder(@NonNull View itemView) {
            super(itemView);
            item_tv = itemView.findViewById(R.id.item_tv);
            item_btn = itemView.findViewById(R.id.item_btn);
            item = itemView.findViewById(R.id.item_layout);
        }
    }

    /**
     * 传入自己的title布局
     */
    public class newtitleHolder extends ViewHolder {
        public newtitleHolder(@NonNull View titleView) {
            super(titleView);
            tit_tv = titleView.findViewById(titletvId);
            tit_btn = titleView.findViewById(titlebtnId);
        }
    }

    /**
     * 传入自己的item布局
     */
    public class newitemHolder extends ViewHolder {
        public newitemHolder(@NonNull View itemView) {
            super(itemView);
            item_tv = itemView.findViewById(itemtvId);
            item_btn = itemView.findViewById(itembtnId);
            item = itemView.findViewById(itemId);
        }
    }
}
