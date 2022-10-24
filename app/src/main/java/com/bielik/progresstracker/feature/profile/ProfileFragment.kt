package com.bielik.progresstracker.feature.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.bielik.progresstracker.base.BaseBindingFragment
import com.bielik.progresstracker.databinding.FragmentProfileBinding
import com.bielik.progresstracker.utils.extensions.loadCircleUserIcon
import com.bielik.progresstracker.utils.extensions.onClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseBindingFragment<FragmentProfileBinding, ProfileViewModel>() {
    override val viewModel by viewModels<ProfileViewModel>()

    private val imagePickerResult = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            viewModel.onImagePicked(it)
            binding?.ivUserIcon?.loadCircleUserIcon(it.toString())
        }
    }

    override fun initUI() {
        implementListeners()
        subscribe()
    }

    private fun implementListeners() = withBinding {
        ivEditIcon.onClick { imagePickerResult.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
    }

    private fun subscribe() = withBinding {
        viewModel.setupFlow.observeWhenResumed {
            tvUserName.text = it?.userName
            ivUserIcon.loadCircleUserIcon(it?.userImage)
        }
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentProfileBinding.inflate(inflater, container, false)

}