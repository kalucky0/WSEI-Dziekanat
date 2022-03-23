package dev.kalucky0.wsei.ui.games

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import dev.kalucky0.wsei.MainActivity
import dev.kalucky0.wsei.R
import dev.kalucky0.wsei.games.Snake

class SnakeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_snake, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = activity as MainActivity
        mainActivity.updateTitle("Snake")
        mainActivity.updateSubtitle("Score: 0")

        val layout = view.findViewById<FrameLayout>(R.id.snake_layout)
        val snake = view.findViewById<Snake>(R.id.snake_game)

        layout.requestFocus()

        snake.onScoreUpdate = {
            mainActivity.runOnUiThread {
                mainActivity.updateSubtitle("Score: $it")
            }
        }

        layout.setOnClickListener {
            snake.changeDirection()
        }
    }
}