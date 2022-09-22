package com.kontrakanprojects.appgamequiz.view.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kontrakanprojects.appgamequiz.R
import com.kontrakanprojects.appgamequiz.databinding.FragmentMainBinding
import com.kontrakanprojects.appgamequiz.view.game.GameActivity

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnGame.setOnClickListener { moveToGame() }
        }
    }

    private fun moveToGame() {
        startActivity(Intent(requireContext(), GameActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}