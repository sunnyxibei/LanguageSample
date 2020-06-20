package com.timeriver.languagesample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.timeriver.languagesample.R
import com.timeriver.languagesample.ext.setOnLimitClickListener
import com.timeriver.languagesample.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_coroutines.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CoroutinesFragment : Fragment() {

    private val viewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coroutines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_test_flow.setOnLimitClickListener {
            viewModel.testFlow()
        }
        bt_test_channel.setOnLimitClickListener {
            viewModel.testChannel()
        }
        bt_test_sequence.setOnLimitClickListener {
            viewModel.testSequence()
        }
        bt_test_start.setOnLimitClickListener {
            viewModel.testCoroutineStart()
        }
        bt_test_with_context.setOnLimitClickListener {
            viewModel.testWithContext()
        }
        bt_test_coroutines.setOnLimitClickListener {
            viewModel.testCoroutinesBlock()
        }
        bt_test_coroutines_non_block1.setOnLimitClickListener {
            viewModel.testCoroutinesNonBlockWithAsync()
        }
        bt_test_coroutines_non_block2.setOnLimitClickListener {
            viewModel.testCoroutinesNonBlockWithLaunch()
        }
    }

}