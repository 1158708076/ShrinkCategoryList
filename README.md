# 介绍
一个自动分类，可折叠的recycleview

# 使用方法：
1、在project gradle里添加
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

2、在模块的 gradle里添加
dependencies {
	        implementation 'com.github.1158708076:ShrinkCategoryList:v1.1'
	}
# 在实例中使用：
1、布局为recycleview

2、适配器使用shrinkcategoryAdapter

3、大概需要设置这些东西：
	mLayoutManager = new MyGridLayoutManager(getContext(), 4, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setScrollEnabled(false);//防止scrollview和recycleview都滚动造成混乱
        list.setLayoutManager(mLayoutManager);
        scadapter = new shrinkcategoryAdapter(getContext(), mData);
        scadapter.setColumNum(4).setVisiblenum(4);
        list.setAdapter(scadapter);
	
4、注：使用对象必须继承库内的itemModel
