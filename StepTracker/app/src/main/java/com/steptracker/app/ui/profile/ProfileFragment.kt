package com.steptracker.app.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.steptracker.app.MainViewModel
import com.steptracker.app.data.UserProfile
import com.steptracker.app.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val vm: MainViewModel by activityViewModels()
    private var ignoreChanges = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vm.profile.observe(viewLifecycleOwner) { p ->
            if (ignoreChanges) return@observe
            ignoreChanges = true
            binding.etWeight.setText(p.weightKg.toString())
            binding.etHeight.setText(p.heightCm.toString())
            binding.etAge.setText(p.ageYears.toString())
            binding.rgSex.check(
                if (p.isMale) binding.rbMale.id else binding.rbFemale.id
            )
            binding.sbGoal.progress = (p.dailyGoalSteps - 1000) / 500
            binding.tvGoalValue.text = "%,d кроків".format(p.dailyGoalSteps).replace(',', ' ')
            binding.sbStride.progress = p.strideLengthCm - 40
            binding.tvStrideValue.text = "${p.strideLengthCm} см"
            updateBmiCard(p)
            ignoreChanges = false
        }

        binding.sbGoal.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
                val goal = 1000 + progress * 500
                binding.tvGoalValue.text = "%,d кроків".format(goal).replace(',', ' ')
                if (fromUser && !ignoreChanges) saveProfile()
            }
            override fun onStartTrackingTouch(sb: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {}
        })

        binding.sbStride.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvStrideValue.text = "${40 + progress} см"
                if (fromUser && !ignoreChanges) saveProfile()
            }
            override fun onStartTrackingTouch(sb: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {}
        })

        binding.btnSave.setOnClickListener { saveProfile() }
    }

    private fun saveProfile() {
        val weight = binding.etWeight.text.toString().toFloatOrNull() ?: 70f
        val height = binding.etHeight.text.toString().toIntOrNull() ?: 170
        val age = binding.etAge.text.toString().toIntOrNull() ?: 25
        val isMale = binding.rbMale.isChecked
        val goal = 1000 + binding.sbGoal.progress * 500
        val stride = 40 + binding.sbStride.progress

        val p = UserProfile(weight, height, age, isMale, goal, stride)
        vm.saveProfile(p)
        updateBmiCard(p)
    }

    private fun updateBmiCard(p: UserProfile) {
        val bmi = p.bmi
        val bmiLabel = when {
            bmi < 18.5f -> "Недостатня вага"
            bmi < 25f -> "Нормальна вага"
            bmi < 30f -> "Надмірна вага"
            else -> "Ожиріння"
        }
        val bmr = p.bmr.toInt()
        val goalKcal = p.calcCalories(p.dailyGoalSteps).toInt()

        binding.tvBmi.text = "ІМТ: ${"%.1f".format(bmi)} — $bmiLabel"
        binding.tvBmr.text = "Базовий обмін: ~$bmr ккал/день"
        binding.tvGoalKcal.text = "При досягненні мети: ~$goalKcal ккал витрачається"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
