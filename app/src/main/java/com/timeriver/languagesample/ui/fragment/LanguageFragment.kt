package com.timeriver.languagesample.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.timeriver.languagesample.domain.model.LanguageType
import com.timeriver.languagesample.R
import com.timeriver.languagesample.SharedPreferencesService
import com.timeriver.languagesample.util.changeAppLanguage
import com.timeriver.languagesample.ui.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_language.*
import timber.log.Timber

class LanguageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_language, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onCustomBack()
                Timber.d("LanguageFragment, handleOnBackPressed")
            }
        }
        callback.isEnabled = false
        //责任链模式，调用顺序跟添加顺序相反，所以dispatcher内部维护了一个栈结构？
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_en.setOnClickListener {
            changeLanguage(LanguageType.ENGLISH.localName)
        }
        button_zh_cn.setOnClickListener {
            changeLanguage(LanguageType.SIMPLIFIED_CHINESE.localName)
        }
        button_zh_tw.setOnClickListener {
            changeLanguage(LanguageType.TRADITIONAL_CHINESE.localName)
        }
        button_reset.setOnClickListener {
            changeLanguage("")
        }
    }

    private fun changeLanguage(language: String) {
        Timber.d("LanguageFragment, changeLanguage $language")
        SharedPreferencesService.instance.language = language
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            context?.changeAppLanguage()
        }
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        requireActivity().finish()
        //android.os.Process.killProcess(android.os.Process.myPid());
    }

    private fun onCustomBack() {

    }

}