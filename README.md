#  一个添加银行卡操作步骤自定义View,很简单<br>
![](https://github.com/wenzhimin/BootStepView/blob/master/apk/bank1.jpg)<br>
![](https://github.com/wenzhimin/BootStepView/blob/master/apk/bank2.jpg)<br>
![](https://github.com/wenzhimin/BootStepView/blob/master/apk/bank3.jpg)<br>
## 继承View 实现自定义属性(不知道自定义属性的百度)<br>
public BootStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {<br>
        super(context, attrs, defStyleAttr);<br>
        // 加载自定义属性集合BootStepView<br>
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BootStepView);<br>
        // 解析集合中的属性属性<br>
        // 将解析的属性传入到画圆的画笔颜色变量当中（本质上是自定义画圆画笔的颜色）<br>
        // 第二个参数是默认设置颜色（即无指定情况下使用）<br>
        circularColor = typedArray.getColor(R.styleable.BootStepView_circular_color, Color.RED);<br>
        circularSize=typedArray.getDimensionPixelSize(R.styleable.BootStepView_circular_size,16);<br>
        circularTextSize=typedArray.getDimensionPixelSize(R.styleable.BootStepView_circular_text_size,60);<br>
        circularTextColor= typedArray.getColor(R.styleable.BootStepView_circular_text_color, Color.WHITE);<br>
        ....<br>
    }<br>
## 在onMeasure()对View的宽高进行测量<br>
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {<br>
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);<br>
        // 获取宽-测量规则的模式和大小<br>
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);<br>
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);<br>
        // 获取高-测量规则的模式和大小<br>
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);<br>
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);<br>
        int width=480;<br>
        int height=240;<br>
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {<br>
            setMeasuredDimension(width, height);<br>
        } else if (widthMode == MeasureSpec.AT_MOST ) {<br>
            setMeasuredDimension(width, heightSize);<br>
        }else if(heightMode== MeasureSpec.AT_MOST){<br>
            setMeasuredDimension(widthSize, height);<br>
        }<br>
    }<br>
    ## 在onDraw()实现<br>
    protected void onDraw(Canvas canvas) {<br>
        super.onDraw(canvas);<br>
        // 获取传入的padding值<br>
        final int paddingLeft = getPaddingLeft();<br>
        final int paddingRight = getPaddingRight();<br>
        final int paddingTop = getPaddingTop();<br>
        final int paddingBottom = getPaddingBottom();<br>
        int width=getWidth();<br>
        //开始绘制第一个圆<br>
        int oneX=paddingLeft+circularSize*2;<br>
        canvas.drawCircle(oneX,paddingTop,circularSize,paint);<br>
        //开始绘制数字1<br>
        paint.setColor(circularTextColor);<br>
        paint.setTextSize(circularTextSize);<br>
        canvas.drawText("1",oneX-circularTextSize/3,paddingTop+circularTextSize/3,paint);<br>
        //开始绘制第一个线条<br>
        paint.setColor(isTwoColor?circularColor:defaultColor);<br>
        canvas.drawLine(oneX+circularSize,paddingTop,width/2-circularSize,paddingTop,paint);<br>
        ...<br>
    }<br>

