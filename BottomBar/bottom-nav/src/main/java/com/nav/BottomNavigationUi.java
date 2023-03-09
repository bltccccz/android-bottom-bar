package com.nav;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

public class BottomNavigationUi extends LinearLayout implements View.OnClickListener {
    /**
     * 定义其中组件
     */
    private ImageButton imgBtnFirst, imgBtnSecond, imgBtnThird, imgBtnFourth, imgBtnCenter;
    private TextView btnLabelFirst, btnLabelSecond, btnLabelThird, btnLabelFourth;
    final Path path = new Path();
    final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * 底部导航栏布局视图
     */
    private View navLayout;
    /**
     * 导航按钮未选中时的图片
     */
    private final Object[] notSelectedImg = new Object[]{R.drawable.nav, R.drawable.nav, R.drawable.nav, R.drawable.nav};
    /**
     * 导航按钮选中时的图片
     */
    private final Object[] selectedImg = new Object[4];
    /**
     * 是否拥有选中后的交换图片
     */
    private boolean haveSelectedImage = false;
    /**
     * 是否需要清除导航栏文字描述
     */
    private boolean clearBtnText = false;
    /**
     * 上一个选项中展示的导航文本
     */
    private TextView lastSelected;
    /**
     * 上一个选项的按钮实例
     */
    private ImageView lastSelectedImage;
    /**
     * 上一个选项中展示的图片下标
     */
    private int lastSelectedIndex = 0;
    /**
     * 导航栏绘制参数
     */
    private float widthRate;
    private int pullHeight;
    private int paintColor;
    private int paintHeight;
    private float shadowRadius;
    private float shadowDx;
    private float shadowDy;
    private int shadowColor;

    private NavClickListener clickListener;

    public BottomNavigationUi(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setWillNotDraw(false);
        this.initializedNavigationComponents(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.drawSide(canvas);
        super.onDraw(canvas);
    }

    @Override
    public void onClick(View v) {
        if (R.id.img_btn_first == v.getId()) {
            selectPage((ImageView) v, 1);
            clickListener.clickNavFirst();
        }
        if (R.id.img_btn_second == v.getId()) {
            selectPage((ImageView) v, 2);
            clickListener.clickNavSecond();
        }
        if (R.id.img_btn_third == v.getId()) {
            selectPage((ImageView) v, 3);
            clickListener.clickNavThird();
        }
        if (R.id.img_btn_fourth == v.getId()) {
            selectPage((ImageView) v, 4);
            clickListener.clickNavFourth();
        }
        if (R.id.img_btn_center == v.getId()) {
            clickListener.clickCenter();
        }
    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void setImageShow(ImageView view, Object image) {
        if (image instanceof String) {
            Glide.with(this).load(image).into(view);
        } else {
            view.setImageResource((Integer) image);
        }
    }

    private void selectImage(ImageView view, int index) {
        if (haveSelectedImage) {
            Object notSelected = this.notSelectedImg[this.lastSelectedIndex];
            setImageShow(this.lastSelectedImage, notSelected);
            this.lastSelectedIndex = index;

            Object o = this.selectedImg[index];
            if (o != null) {
                setImageShow(view, o);
            }
        }
    }

    private void selectPage(ImageView view, int pageNo) {
        switch (pageNo) {
            case 1:
                if (!clearBtnText) {
                    this.lastSelected.setVisibility(GONE);
                    this.btnLabelFirst.setVisibility(VISIBLE);
                    this.lastSelected = this.btnLabelFirst;
                }
                this.selectImage(view, 0);
                this.lastSelectedImage = this.imgBtnFirst;
                break;
            case 2:
                if (!clearBtnText) {
                    this.lastSelected.setVisibility(GONE);
                    this.btnLabelSecond.setVisibility(VISIBLE);
                    this.lastSelected = this.btnLabelSecond;
                }
                this.selectImage(view, 1);
                this.lastSelectedImage = this.imgBtnSecond;
                break;
            case 3:
                if (!clearBtnText) {
                    this.lastSelected.setVisibility(GONE);
                    this.btnLabelThird.setVisibility(VISIBLE);
                    this.lastSelected = this.btnLabelThird;
                }
                this.selectImage(view, 2);
                this.lastSelectedImage = this.imgBtnThird;
                break;
            case 4:
                if (!clearBtnText) {
                    this.lastSelected.setVisibility(GONE);
                    this.btnLabelFourth.setVisibility(VISIBLE);
                    this.lastSelected = this.btnLabelFourth;
                }
                this.selectImage(view, 3);
                this.lastSelectedImage = this.imgBtnFourth;
                break;
        }
    }

    private void initializedNavigationComponents(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.navigation_layout, this);

        this.navLayout = view.findViewById(R.id.nav_layout);

        this.imgBtnFirst = view.findViewById(R.id.img_btn_first);
        this.imgBtnSecond = view.findViewById(R.id.img_btn_second);
        this.imgBtnThird = view.findViewById(R.id.img_btn_third);
        this.imgBtnFourth = view.findViewById(R.id.img_btn_fourth);
        this.imgBtnCenter = view.findViewById(R.id.img_btn_center);

        this.imgBtnFirst.setOnClickListener(this);
        this.imgBtnSecond.setOnClickListener(this);
        this.imgBtnThird.setOnClickListener(this);
        this.imgBtnFourth.setOnClickListener(this);

        this.btnLabelFirst = view.findViewById(R.id.btn_label_first);
        this.btnLabelSecond = view.findViewById(R.id.btn_label_second);
        this.btnLabelThird = view.findViewById(R.id.btn_label_third);
        this.btnLabelFourth = view.findViewById(R.id.btn_label_fourth);

        this.lastSelected = this.btnLabelFirst;
        this.lastSelectedImage = this.imgBtnFirst;
    }

    private void drawSide(Canvas canvas) {
        final int width = getResources().getDisplayMetrics().widthPixels;
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(this.paintColor);
        paint.setShadowLayer(this.shadowRadius, this.shadowDx, this.shadowDy, this.shadowColor);
        //计算起始点横坐标
        final float rate = width / this.widthRate;
        //控制点横坐标，固定位屏幕中间位置
        final float se = width / 2f;
        //移动画笔到导航左上角
        path.moveTo(0, dip2px(this.pullHeight));
        //划线到贝塞尔曲线起始点位置
        path.lineTo(rate, dip2px(this.pullHeight));
        //绘制贝塞尔曲线
        path.quadTo(se, dip2px(-this.pullHeight), width - rate, dip2px(this.pullHeight));
        //填充未关联部分
        path.lineTo(width, dip2px(pullHeight));
        //填充整个框，绘制背景
        path.lineTo(width, dip2px(this.paintHeight));
        path.lineTo(0, dip2px(this.paintHeight));
        path.close();
        canvas.drawPath(path, paint);
    }

    /**
     * 初始化设定完成的组件
     */
    public void build() {
        this.selectPage(this.imgBtnFirst, 1);
    }

    /**
     * 设置导航栏高度
     *
     * @param bottomNavHeight 导航栏高度
     * @param marginBottom    距离底部距离
     * @return 导航对象
     */
    public BottomNavigationUi setBottomNavHeightAndMarginBottom(int bottomNavHeight, int marginBottom) {
        LayoutParams layoutParams = (LayoutParams) this.navLayout.getLayoutParams();
        layoutParams.height = this.dip2px(bottomNavHeight);
        layoutParams.bottomMargin = this.dip2px(marginBottom);
        return this;
    }

    /**
     * 设置导航栏按钮图片
     *
     * @param centerRes 中间按钮图片
     * @param otherRes  其他按钮图片(按顺序设置)
     * @return 导航对象
     */
    public BottomNavigationUi setBtnImage(int centerRes, int... otherRes) {
        this.imgBtnCenter.setImageResource(centerRes);
        if (otherRes == null) {
            return this;
        }
        for (int i = 0; i < otherRes.length; i++) {
            this.notSelectedImg[i] = otherRes[i];
            switch (i) {
                case 0:
                    this.imgBtnFirst.setImageResource(otherRes[i]);
                    break;
                case 1:
                    this.imgBtnSecond.setImageResource(otherRes[i]);
                    break;
                case 2:
                    this.imgBtnThird.setImageResource(otherRes[i]);
                    break;
                case 3:
                    this.imgBtnFourth.setImageResource(otherRes[i]);
                    break;
                default:
                    break;
            }
        }
        return this;
    }

    /**
     * 设置按钮图片
     *
     * @param centerUrl 中间按钮图片url
     * @param otherUrl  其他按钮图片url(按顺序设置)
     * @return 导航对象
     */
    public BottomNavigationUi setBtnImage(String centerUrl, String... otherUrl) {
        Glide.with(this).load(centerUrl).into(this.imgBtnCenter);
        if (otherUrl == null) {
            return this;
        }
        for (int i = 0; i < otherUrl.length; i++) {
            this.notSelectedImg[i] = otherUrl[i];
            switch (i) {
                case 0:
                    Glide.with(this).load(otherUrl[i]).into(this.imgBtnFirst);
                    break;
                case 1:
                    Glide.with(this).load(otherUrl[i]).into(this.imgBtnSecond);
                    break;
                case 2:
                    Glide.with(this).load(otherUrl[i]).into(this.imgBtnThird);
                    break;
                case 3:
                    Glide.with(this).load(otherUrl[i]).into(this.imgBtnFourth);
                    break;
                default:
                    break;
            }
        }
        return this;
    }

    /**
     * 设置选中时的图片
     *
     * @param images 选中时的图片数组（按顺序设置)
     * @return 导航对象
     */
    public BottomNavigationUi setSelectedImage(Object... images) {
        if (images == null) {
            return this;
        }
        System.arraycopy(images, 0, this.selectedImg, 0, images.length);
        this.haveSelectedImage = true;
        return this;
    }

    /**
     * 清除导航按钮下方文字
     *
     * @return 导航对象
     */
    public BottomNavigationUi clearBtnText() {
        this.clearBtnText = true;
        return this;
    }

    /**
     * 设置文本大小
     *
     * @param textSize 文本大小
     * @param color    文本颜色
     * @return 导航对象
     */
    public BottomNavigationUi setBtnText(int textSize, int color) {
        this.btnLabelFirst.setTextSize(textSize);
        this.btnLabelSecond.setTextSize(textSize);
        this.btnLabelThird.setTextSize(textSize);
        this.btnLabelFourth.setTextSize(textSize);

        this.btnLabelFirst.setTextColor(color);
        this.btnLabelSecond.setTextColor(color);
        this.btnLabelThird.setTextColor(color);
        this.btnLabelFourth.setTextColor(color);
        return this;
    }

    /**
     * 设置按钮容器
     *
     * @param distanceBorder  图片和边的距离
     * @param distanceText    图片和文本的距离
     * @param navImageSize    图片的大小
     * @param centerImageSize 中心图片的大小
     * @return 导航对象
     */
    public BottomNavigationUi setImageContainer(int distanceBorder, int distanceText, int navImageSize, int centerImageSize) {
        LayoutParams nav = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, navImageSize);

        nav.setMargins(distanceBorder, distanceBorder, distanceBorder, distanceText);

        this.imgBtnFirst.setLayoutParams(nav);
        this.imgBtnSecond.setLayoutParams(nav);
        this.imgBtnThird.setLayoutParams(nav);
        this.imgBtnFourth.setLayoutParams(nav);

        RelativeLayout.LayoutParams center = new RelativeLayout.LayoutParams(centerImageSize, centerImageSize);
        center.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.imgBtnCenter.setLayoutParams(center);
        return this;
    }

    /**
     * 设置导航文本
     *
     * @param text 导航文本，按顺序排列
     * @return 导航对象
     */
    public BottomNavigationUi setNavText(@NonNull String... text) {
        for (int i = 0; i < text.length; i++) {
            switch (i) {
                case 0:
                    this.btnLabelFirst.setText(text[i]);
                    break;
                case 1:
                    this.btnLabelSecond.setText(text[i]);
                    break;
                case 2:
                    this.btnLabelThird.setText(text[i]);
                    break;
                case 3:
                    this.btnLabelFourth.setText(text[i]);
                    break;
                default:
                    break;
            }
        }
        return this;
    }

    /**
     * 绘制导航
     *
     * @param widthRate    绘制贝塞尔曲线
     * @param pullHeight   贝赛尔曲线开始位置计算参数
     * @param paintHeight  贝塞尔曲线拉起高度
     * @param paintColor   绘制导航背景色
     * @param shadowRadius 导航描边阴影模糊率
     * @param shadowDx     描边阴影x偏移量
     * @param shadowDy     描边阴影y偏移量
     * @param shadowColor  阴影颜色
     * @return 导航对象
     */
    public BottomNavigationUi drawBessel(float widthRate,
                                         int pullHeight,
                                         int paintHeight,
                                         int paintColor,
                                         float shadowRadius,
                                         float shadowDx,
                                         float shadowDy,
                                         int shadowColor) {
        this.widthRate = widthRate;
        this.pullHeight = pullHeight;
        this.paintHeight = paintHeight;
        this.paintColor = paintColor;
        this.shadowRadius = shadowRadius;
        this.shadowDx = shadowDx;
        this.shadowDy = shadowDy;
        this.shadowColor = shadowColor;
        return this;
    }

    /**
     * 设置导航栏点击事件监听
     *
     * @param listener 点击事件监听
     * @return 导航对象
     */
    public BottomNavigationUi setNavClickListener(NavClickListener listener) {
        this.clickListener = listener;
        return this;
    }
}
