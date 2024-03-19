package pet.perpet.equal.presentation.base.widget

import android.content.Context
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.ControllerListener
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.image.ImageInfo
import pet.perpet.framework.nonnull
import pet.perpet.framework.util.Logger


class BaseSimpleDraweeView : SimpleDraweeView, Checkable {

    //======================================================================
    // Variables
    //======================================================================

    var check: Boolean = false

    private var imageLoadCompleteCallback: (() -> Unit)? = null

    private var imageLoadFailCallback: (() -> Unit)? = null

    private var imageStartCallback: (() -> Unit)? = null

    private var notSyncImage: Boolean = false

    private var imageSyncLayoutRatio = false

    var image: String? = null

    private val imageControllerListener = object : ControllerListener<ImageInfo> {
        override fun onFailure(id: String?, throwable: Throwable?) {
            /*binding.shimmer.stopShimmer()*/
            log("onFailure > id : $id, message : ${throwable?.message.orEmpty()}")
            imageLoadFailCallback?.invoke()
        }

        override fun onRelease(id: String?) {
            /*binding.shimmer.stopShimmer()*/
            log("onRelease")
        }

        override fun onSubmit(id: String?, callerContext: Any?) {
            log("onSubmit")
            imageStartCallback?.let {
                it()
            }
        }

        override fun onIntermediateImageSet(id: String?, imageInfo: ImageInfo?) {
            log("onIntermediateImageSet > id : $id, w : ${imageInfo?.width},  h : ${imageInfo?.height}")
        }

        override fun onIntermediateImageFailed(id: String?, throwable: Throwable?) {
            log("onIntermediateImageFailed : $id")
        }

        override fun onFinalImageSet(
            id: String?,
            imageInfo: ImageInfo?,
            animatable: Animatable?
        ) {

            val viewRatio = width.toFloat() / height.toFloat()
            val imageRatio =
                imageInfo?.width?.toFloat().nonnull(1f) / imageInfo?.height?.toFloat().nonnull(1f)

            if (imageSyncLayoutRatio == true && viewRatio != imageRatio) {
                layoutParams.run {
                    if (this is ConstraintLayout.LayoutParams) {
                        this
                    } else {
                        null
                    }
                }?.run {
                    this.dimensionRatio =
                        "${imageInfo?.width.nonnull()}:${imageInfo?.height.nonnull()}"
                }
            }

            log("onFinalImageSet > id : $id, w : ${imageInfo?.width},  h : ${imageInfo?.height}, ratio : ${viewRatio} => view w : ${width}, h : ${height}, ratio : ${imageRatio}")
            animatable?.start()
            imageLoadCompleteCallback?.let {
                it()
            }

        }
    }

    //======================================================================
    // Constructor
    //======================================================================

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            AppCompatImageView.mergeDrawableStates(drawableState, CHECKED_STATE)
        }
        return drawableState
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        invalidate()
    }

    override fun isChecked(): Boolean {
        return check
    }

    override fun toggle() {
        isChecked = !isChecked
    }

    override fun setChecked(checked: Boolean) {
        check = checked
        refreshDrawableState()
    }

    override fun setOnClickListener(onClickListener: OnClickListener?) {
        super.setOnClickListener(OnClickListenerWarp(onClickListener))
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        imageLoadCompleteCallback?.let {
            it()
        }
    }

    fun setTopLeftConnerRadius(value: Float) {
        setConnerRadius(topL = value)
    }

    fun setTopRightConnerRadius(value: Float) {
        setConnerRadius(topR = value)
    }

    fun setBottomRightConnerRadius(value: Float) {
        setConnerRadius(bottomR = value)
    }

    fun setBottomLeftConnerRadius(value: Float) {
        setConnerRadius(bottomL = value)
    }

    fun setConnerRadius(
        topL: Float = 0f,
        topR: Float = 0f,
        bottomL: Float = 0f,
        bottomR: Float = 0f
    ) {
        val p = hierarchy?.roundingParams?.cornersRadii
        val topLeft = if (topL <= 0) p?.get(0).nonnull() else topL
        val topRight = if (topR <= 2) p?.get(2).nonnull() else topR
        val bottomRight = if (bottomR <= 4) p?.get(4).nonnull() else bottomR
        val bottomLeft = if (bottomL <= 6) p?.get(6).nonnull() else bottomL
        hierarchy.roundingParams =
            RoundingParams.fromCornersRadii(topLeft, topRight, bottomRight, bottomLeft)
        invalidate()
    }

    fun callbackLoadComplete(value: (() -> Unit)) {
        imageLoadCompleteCallback = value
    }

    fun callbackFail(value: (() -> Unit)) {
        imageLoadFailCallback = value
    }

    fun log(message: String) {
        Logger.w(this::class.java.simpleName, message)
    }

    //======================================================================
    // Public Methods
    //======================================================================

    fun setImageSyncLayoutRatio(sync: Boolean) {
        imageSyncLayoutRatio = sync
    }

    fun setNotSyncImage(sync: Boolean) {
        notSyncImage = sync
    }

    fun setActualImageScaleType(type: ScalingUtils.ScaleType) {
        hierarchy.actualImageScaleType = type
    }

    fun setSupportImageUrl(url: String?) {
        val controller = Fresco.newDraweeControllerBuilder()
            .setUri(url)
            .setAutoPlayAnimations(true)
            .setControllerListener(imageControllerListener)
            .build()
        setController(controller)
    }

    fun setSupportImageUrl(url: Any?) {
        if (url is String) {
            setSupportImageUrl(url)
        } else if (url is Uri) {
            setSupportImageUrl(url)
        }
    }

    fun setSupportImageUrl(url: Uri?) {
        if (url == null) {
            return
        }
        log("setSupportImageUrl > $url")
        if (notSyncImage) {
            if ((url.toString() == image?.nonnull()).nonnull() == false) {
                val controller = Fresco.newDraweeControllerBuilder()
                    .setUri(url)
                    .setAutoPlayAnimations(true)
                    .setControllerListener(imageControllerListener)
                    .build()
                setController(controller)
            }
        } else {
            val controller = Fresco.newDraweeControllerBuilder()
                .setUri(url)
                .setAutoPlayAnimations(true)
                .setControllerListener(imageControllerListener)
                .build()
            setController(controller)
        }
        image = url.toString()
    }

    //======================================================================
    // companion
    //======================================================================

    companion object {
        val CHECKED_STATE = intArrayOf(android.R.attr.state_checked)

        @JvmStatic
        @BindingAdapter("setSupportImageUrl")
        fun setImageUrl(view: BaseSimpleDraweeView, value: String?) {
            view.setSupportImageUrl(value)
        }

        @JvmStatic
        @BindingAdapter("setSupportImageUrl")
        fun setImageUrl(view: BaseSimpleDraweeView, value: Uri?) {
            view.setSupportImageUrl(value)
        }

        @JvmStatic
        @BindingAdapter("setSupportImageUrl")
        fun setImageUrl(view: BaseSimpleDraweeView, value: Any?) {
            view.setSupportImageUrl(value)
        }

        @JvmStatic
        @BindingAdapter("setImageURI")
        fun setImageURI(view: BaseSimpleDraweeView, value: String?) {
            view.setImageURI(value)
        }

        @JvmStatic
        @BindingAdapter("setImageURI")
        fun setImageURI(view: BaseSimpleDraweeView, value: Uri?) {
            view.setImageURI(value)
        }

        @JvmStatic
        @BindingAdapter("setTopLeftConnerRadius")
        fun setTopLeftRadius(view: BaseSimpleDraweeView, value: Float) {
            view.setTopLeftConnerRadius(value)
        }

        @JvmStatic
        @BindingAdapter("setTopRightConnerRadius")
        fun setTopRightRadius(view: BaseSimpleDraweeView, value: Float) {
            view.setTopRightConnerRadius(value)
        }

        @JvmStatic
        @BindingAdapter("setBottomLeftConnerRadius")
        fun setBottomLeftRadius(view: BaseSimpleDraweeView, value: Float) {
            view.setBottomLeftConnerRadius(value)
        }

        @JvmStatic
        @BindingAdapter("setBottomRightConnerRadius")
        fun setBottomRightRadius(view: BaseSimpleDraweeView, value: Float) {
            view.setBottomRightConnerRadius(value)
        }

        @JvmStatic
        @BindingAdapter("setNotSyncImage")
        fun setNotSyncImage(view: BaseSimpleDraweeView, value: Boolean) {
            view.notSyncImage = value
        }

        @JvmStatic
        @BindingAdapter("setAspectRatio")
        fun setAspectRatio(view: BaseSimpleDraweeView, value: Float) {
            view.aspectRatio = value
        }

        @JvmStatic
        @BindingAdapter("setActualImageScaleType")
        fun setActualImageScaleType(view: BaseSimpleDraweeView, type: ScalingUtils.ScaleType?) {
            if (type != null) {
                view.setActualImageScaleType(type)
            }
        }

        @JvmStatic
        @BindingAdapter("setImageSyncLayoutRatio")
        fun setImageSyncLayoutRatio(view: BaseSimpleDraweeView, sync: Boolean) {
            view.imageSyncLayoutRatio = sync
        }

        @JvmStatic
        @BindingAdapter("callbackFail")
        fun setCallbackFail(view: BaseSimpleDraweeView, callback: (() -> Unit)) {
            view.callbackFail(callback)
        }

        @JvmStatic
        @BindingAdapter("callbackLoadComplete")
        fun setCallbackLoadComplete(view: BaseSimpleDraweeView, callback: (() -> Unit)) {
            view.callbackLoadComplete(callback)
        }
    }

    //======================================================================
    // OnClickListenerWarp
    //======================================================================

    internal inner class OnClickListenerWarp(private val listener: OnClickListener?) :
        OnClickListener {

        override fun onClick(view: View) {
            toggle()
            listener?.onClick(view)
        }
    }
}