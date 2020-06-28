package io.github.karadkar.sample.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import io.github.karadkar.sample.R
import io.github.karadkar.sample.databinding.FragDetailBinding

class DetailFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragDetailBinding

    private lateinit var pageTitle: String
    private lateinit var pageUrl: String

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.frag_detail, container, false)

        arguments?.apply {
            pageTitle = getString(KEY_TITLE)!!
            pageUrl = getString(KEY_URL)!!
        }
        binding.apply {
            tvTitle.text = pageTitle
            btnBack.setOnClickListener(this@DetailFragment)
            if (Patterns.WEB_URL.matcher(pageUrl).matches()) {
                webView.loadUrl(pageUrl)
                webView.settings.javaScriptEnabled = true
            } else {
                Toast.makeText(requireContext(), "Invalid Url", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_back) {
            activity?.onBackPressed()
        }
    }

    companion object {
        private const val KEY_TITLE = "key.title"
        private const val KEY_URL = "key.url"
        fun getInstance(title: String, url: String): DetailFragment {
            return DetailFragment().also {
                it.arguments = Bundle().apply {
                    putString(KEY_TITLE, title)
                    putString(KEY_URL, url)
                }
            }
        }
    }
}