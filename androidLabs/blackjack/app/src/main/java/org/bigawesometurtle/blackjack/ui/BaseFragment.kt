package org.bigawesometurtle.blackjack.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/*Базовый фрагмент, используется чтобы не писать
этот код во всех фрагментах.
 */
abstract class BaseFragment : Fragment() {
    abstract val layoutRes: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(layoutRes, container, false)
}