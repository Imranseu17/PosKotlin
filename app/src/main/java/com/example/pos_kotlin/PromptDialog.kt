package com.example.pos_kotlin


import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Bundle
import android.view.View
import android.view.ViewGroup

import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import java.security.AccessController.getContext

class PromptDialog @JvmOverloads constructor(context: Context, theme: Int = 0):
    Dialog (context, R.style.color_dialog) {

    private var mAnimIn: AnimationSet? = null
    private var mAnimOut: AnimationSet? = null
    private var mDialogView: View? = null
    var titleTextView: TextView? = null
        private set
    var contentTextView: TextView? = null
        private set
    private var mPositiveBtn: TextView? = null
    private var mOnPositiveListener: OnPositiveListener? = null

    private var mDialogType: Int = 0
    private var mIsShowAnim: Boolean = false
    private var mTitle: CharSequence? = null
    private var mContent: CharSequence? = null
    private var mBtnText: CharSequence? = null

    init {
        init()
    }

    private fun init() {
        mAnimIn = AnimationLoader.getInAnimation(getContext())
        mAnimOut = AnimationLoader.getOutAnimation(getContext())
    }


    protected override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)

        initView()

        initListener()

    }

    private fun initView() {
        val contentView = View.inflate(getContext(), R.layout.layout_promptdialog, null)
        setContentView(contentView)
        resizeDialog()

        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content)
        titleTextView = contentView.findViewById<View>(R.id.tvTitle) as TextView
        contentTextView = contentView.findViewById<View>(R.id.tvContent) as TextView
        mPositiveBtn = contentView.findViewById<View>(R.id.btnPositive) as TextView

        val llBtnGroup = contentView.findViewById<View>(R.id.llBtnGroup)
        val logoIv = contentView.findViewById<View>(R.id.logoIv) as ImageView
        logoIv.setBackgroundResource(getLogoResId(mDialogType))

        val topLayout = contentView.findViewById<View>(R.id.topLayout) as LinearLayout
        val triangleIv = ImageView(getContext())
        triangleIv.layoutParams =
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dp2px(getContext(), 10f))
        triangleIv.setImageBitmap(
            createTriangel(
                (DisplayUtil.getScreenSize(getContext()).x * 0.7).toInt(),
                DisplayUtil.dp2px(getContext(), 10f)
            )
        )
        topLayout.addView(triangleIv)

        setBtnBackground(mPositiveBtn!!)
        setBottomCorners(llBtnGroup)


        val radius = DisplayUtil.dp2px(getContext(), DEFAULT_RADIUS.toFloat())
        val outerRadii =
            floatArrayOf(radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat(), 0f, 0f, 0f, 0f)
        val roundRectShape = RoundRectShape(outerRadii, null, null)
        val shapeDrawable = ShapeDrawable(roundRectShape)
        shapeDrawable.paint.style = Paint.Style.FILL
        shapeDrawable.paint.color = getContext().getResources().getColor(getColorResId(mDialogType))
        val llTop = findViewById(R.id.llTop) as LinearLayout
        llTop.background = shapeDrawable

        titleTextView!!.text = mTitle
        contentTextView!!.text = mContent
        mPositiveBtn!!.text = mBtnText
    }

    private fun resizeDialog() {
        val params = getWindow().getAttributes()
        params.width = (DisplayUtil.getScreenSize(getContext()).x * 0.7).toInt()
        getWindow().setAttributes(params)
    }

    protected override fun onStart() {
        super.onStart()
        startWithAnimation(mIsShowAnim)
    }

    override fun dismiss() {
        dismissWithAnimation(mIsShowAnim)
    }

    private fun startWithAnimation(showInAnimation: Boolean) {
        if (showInAnimation) {
            mDialogView!!.startAnimation(mAnimIn)
        }
    }

    private fun dismissWithAnimation(showOutAnimation: Boolean) {
        if (showOutAnimation) {
            mDialogView!!.startAnimation(mAnimOut)
        } else {
            super.dismiss()
        }
    }

    private fun getLogoResId(mDialogType: Int): Int {
        if (DIALOG_TYPE_DEFAULT == mDialogType) {
            return R.mipmap.ic_info
        }
        if (DIALOG_TYPE_INFO == mDialogType) {
            return R.mipmap.ic_info
        }
        if (DIALOG_TYPE_HELP == mDialogType) {
            return R.mipmap.ic_help
        }
        if (DIALOG_TYPE_WRONG == mDialogType) {
            return R.mipmap.ic_wrong
        }
        if (DIALOG_TYPE_SUCCESS == mDialogType) {
            return R.mipmap.ic_success
        }
        return if (DIALOG_TYPE_WARNING == mDialogType) {
            R.mipmap.icon_warning
        } else R.mipmap.ic_info
    }

    private fun getColorResId(mDialogType: Int): Int {
        if (DIALOG_TYPE_DEFAULT == mDialogType) {
            return R.color.color_type_info
        }
        if (DIALOG_TYPE_INFO == mDialogType) {
            return R.color.color_type_info
        }
        if (DIALOG_TYPE_HELP == mDialogType) {
            return R.color.color_type_help
        }
        if (DIALOG_TYPE_WRONG == mDialogType) {
            return R.color.color_type_wrong
        }
        if (DIALOG_TYPE_SUCCESS == mDialogType) {
            return R.color.color_type_success
        }
        return if (DIALOG_TYPE_WARNING == mDialogType) {
            R.color.color_type_warning
        } else R.color.color_type_info
    }

    private fun getSelBtn(mDialogType: Int): Int {
        if (DIALOG_TYPE_DEFAULT == mDialogType) {
            return R.drawable.sel_btn
        }
        if (DIALOG_TYPE_INFO == mDialogType) {
            return R.drawable.sel_btn_info
        }
        if (DIALOG_TYPE_HELP == mDialogType) {
            return R.drawable.sel_btn_help
        }
        if (DIALOG_TYPE_WRONG == mDialogType) {
            return R.drawable.sel_btn_wrong
        }
        if (DIALOG_TYPE_SUCCESS == mDialogType) {
            return R.drawable.sel_btn_success
        }
        return if (DIALOG_TYPE_WARNING == mDialogType) {
            R.drawable.sel_btn_warning
        } else R.drawable.sel_btn
    }

    private fun initAnimListener() {
        mAnimOut!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                mDialogView!!.post { callDismiss() }
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    private fun initListener() {
        mPositiveBtn!!.setOnClickListener {
            if (mOnPositiveListener != null) {
                mOnPositiveListener!!.onClick(this@PromptDialog)
            }
        }

        initAnimListener()
    }

    private fun callDismiss() {
        super.dismiss()
    }

    private fun createTriangel(width: Int, height: Int): Bitmap? {
        return if (width <= 0 || height <= 0) {
            null
        } else getBitmap(width, height, getContext().getResources().getColor(getColorResId(mDialogType)))
    }

    private fun getBitmap(width: Int, height: Int, backgroundColor: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, BITMAP_CONFIG)
        val canvas = Canvas(bitmap)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = backgroundColor
        val path = Path()
        path.moveTo(0f, 0f)
        path.lineTo(width.toFloat(), 0f)
        path.lineTo((width / 2).toFloat(), height.toFloat())
        path.close()

        canvas.drawPath(path, paint)
        return bitmap

    }


    private fun setBtnBackground(btnOk: TextView) {
        btnOk.setTextColor(
            createColorStateList(
                getContext().getResources().getColor(getColorResId(mDialogType)),
                getContext().getResources().getColor(R.color.color_dialog_gray)
            )
        )
        btnOk.setBackgroundDrawable(getContext().getResources().getDrawable(getSelBtn(mDialogType)))
    }


    private fun setBottomCorners(llBtnGroup: View) {
        val radius = DisplayUtil.dp2px(getContext(), DEFAULT_RADIUS.toFloat())
        val outerRadii =
            floatArrayOf(0f, 0f, 0f, 0f, radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat())
        val roundRectShape = RoundRectShape(outerRadii, null, null)
        val shapeDrawable = ShapeDrawable(roundRectShape)
        shapeDrawable.paint.color = Color.WHITE
        shapeDrawable.paint.style = Paint.Style.FILL
        llBtnGroup.setBackgroundDrawable(shapeDrawable)
    }

    private fun createColorStateList(
        normal: Int,
        pressed: Int,
        focused: Int = Color.BLACK,
        unable: Int = Color.BLACK
    ): ColorStateList {
        val colors = intArrayOf(pressed, focused, normal, focused, unable, normal)
        val states = arrayOfNulls<IntArray>(6)
        states[0] = intArrayOf(android.R.attr.state_pressed, android.R.attr.state_enabled)
        states[1] = intArrayOf(android.R.attr.state_enabled, android.R.attr.state_focused)
        states[2] = intArrayOf(android.R.attr.state_enabled)
        states[3] = intArrayOf(android.R.attr.state_focused)
        states[4] = intArrayOf(android.R.attr.state_window_focused)
        states[5] = intArrayOf()
        return ColorStateList(states, colors)
    }

    fun setAnimationEnable(enable: Boolean): PromptDialog {
        mIsShowAnim = enable
        return this
    }

    fun setTitleText(title: CharSequence): PromptDialog {
        mTitle = title
        return this
    }

    fun setTitleText(resId: Int): PromptDialog {
        return setTitleText(getContext().getString(resId))
    }

    fun setContentText(content: CharSequence): PromptDialog {
        mContent = content
        return this
    }

    fun setContentText(resId: Int): PromptDialog {
        return setContentText(getContext().getString(resId))
    }

    fun setDialogType(type: Int): PromptDialog {
        mDialogType = type
        return this
    }

    fun getDialogType(): Int {
        return mDialogType
    }

    fun setPositiveListener(btnText: CharSequence, l: OnPositiveListener): PromptDialog {
        mBtnText = btnText
        return setPositiveListener(l)
    }

    fun setPositiveListener(stringResId: Int, l: OnPositiveListener): PromptDialog {
        return setPositiveListener(getContext().getString(stringResId), l)
    }

    fun setPositiveListener(l: OnPositiveListener): PromptDialog {
        mOnPositiveListener = l
        return this
    }

    fun setAnimationIn(animIn: AnimationSet): PromptDialog {
        mAnimIn = animIn
        return this
    }

    fun setAnimationOut(animOut: AnimationSet): PromptDialog {
        mAnimOut = animOut
        initAnimListener()
        return this
    }

    interface OnPositiveListener {
        fun onClick(dialog: PromptDialog)
    }

    companion object {


        private val BITMAP_CONFIG = Bitmap.Config.ARGB_8888
        private val DEFAULT_RADIUS = 6
        val DIALOG_TYPE_INFO = 0
        val DIALOG_TYPE_HELP = 1
        val DIALOG_TYPE_WRONG = 2
        val DIALOG_TYPE_SUCCESS = 3
        val DIALOG_TYPE_WARNING = 4
        val DIALOG_TYPE_DEFAULT = DIALOG_TYPE_INFO
    }
}
