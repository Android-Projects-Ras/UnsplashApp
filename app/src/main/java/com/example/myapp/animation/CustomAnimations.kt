import android.animation.ObjectAnimator
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapp.R
import com.example.myapp.databinding.ViewLikeButtonBinding
import com.example.myapp.ui.ACTIVITY_CONTEXT
import com.example.myapp.view.LikeButtonView

class CustomAnimations() {
    private val view =
        ConstraintLayout.inflate(
            ACTIVITY_CONTEXT, R.layout.view_like_button, LikeButtonView(
                ACTIVITY_CONTEXT, null
            )
        )
    private var binding = ViewLikeButtonBinding.bind(view)

    val animX = ObjectAnimator().apply {
        target = binding.ivHeart
        duration = 500
        setPropertyName(View.SCALE_X.name)
        setFloatValues(0.8f, 1.1f, 0.9f, 1.0f)
    }

}