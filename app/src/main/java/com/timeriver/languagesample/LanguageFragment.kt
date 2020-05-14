package com.timeriver.languagesample

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_language.*

class LanguageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_language, container, false)
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
        Log.d("MainActivity", "changeLanguage $language")
        SharedPreferencesService.instance.language = language
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            context?.changeAppLanguage()
        }
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        requireActivity().finish()
//        android.os.Process.killProcess(android.os.Process.myPid());
    }

}