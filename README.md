# TurntableView
Android custom control TurntableView, lottery turntable

Android自定义控件TurntableView，抽奖转盘

<a href="https://github.com/hnsycsxhzcsh/TurntableView/blob/master/myres/turntableview.apk">Download Apk</a>

效果图

<img src="https://github.com/hnsycsxhzcsh/TurntableView/blob/master/myres/turntableview.gif" width="300" height="612">

Step 1. Add the JitPack repository to your build file

步骤1.将JitPack存储库添加到构建文件中

项目的根build.gradle中添加以下代码：

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Step 2. Add the dependency

步骤2.build.gradle添加依赖项

	dependencies {
	        implementation 'com.github.hnsycsxhzcsh:TurntableView:v1.1'
	}
  
Step 3. Reference control in layout(The control needs to be placed in a parent layout, with a picture button in the parent layout.)
  
步骤3. 布局中引用控件（控件需要放在一个父布局中，父布局中放一个图片按钮）

    <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <com.turntableview.TurntableView
                android:id="@+id/turntable"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_centerInParent="true" />

            <ImageView
                android:id="@+id/iv_node"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_centerInParent="true"
                android:background="@mipmap/node" />

        </RelativeLayout>
       
Step 4. Add listener to the activity

步骤4. activity中添加监听

     mIvGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTurntable.startRotate(new ITurntableListener() {
                    @Override
                    public void onStart() {
                        Toast.makeText(MainActivity.this, "开始抽奖", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onEnd(int position, String name) {
                        mTvResult.setText("抽奖结束抽中第" + (position + 1) + "位置的奖品:" + name);
                    }
                });
            }
        });
        
控件的其它方法：(Other methods of the control:)

设置转盘背景item的颜色(Set the color of the turntable background item)

setBackColor(ArrayList<Integer> colors);
  
修改转盘基本数据(Modify the basic data of the turntable)

setDatas(int num, ArrayList<String> names, ArrayList<Bitmap> bitmaps);
	
我的博客地址：https://blog.csdn.net/m0_38074457/article/details/86433483

If my control helps you, please help click on the top right corner, thank you!

如果有帮助到大家希望点下右上角Star，谢谢！

