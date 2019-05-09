package de.pbauerochse.worklogviewer.fx.tasks

import de.pbauerochse.worklogviewer.util.FormattingUtil.getFormatted
import javafx.animation.FadeTransition
import javafx.animation.PauseTransition
import javafx.animation.SequentialTransition
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.concurrent.Worker
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.control.ProgressBar
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.text.Text
import javafx.util.Duration

/**
 * Component that groups a Progressbar, as well as
 * a label for the Task to be completed, and a
 * progress message label
 */
class TaskProgressBar(private val task: WorklogViewerTask<*>) : StackPane(), ChangeListener<Worker.State> {

    @FXML
    lateinit var progressBar: ProgressBar
        private set

    @FXML
    lateinit var taskName: Text
        private set

    @FXML
    lateinit var progressText: Text
        private set

    init {
        val loader = FXMLLoader(this::class.java.getResource("/fx/components/task-progress-bar.fxml"))
        loader.setRoot(this)
        loader.setController(this)
        loader.load<Parent>()
    }

    override fun changed(observable: ObservableValue<out Worker.State>?, oldValue: Worker.State?, newValue: Worker.State?) {
        taskName.text = getTaskNameLabel(newValue)

        if (isCompletedState(newValue)) {
            triggerFadeOut()
        }
    }

    private fun getTaskNameLabel(state: Worker.State?): String {
        val taskName = task.label
        val stateLabel = state?.let { getFormatted("task.state.${it.name.toLowerCase()}") }
        val stateLabelWithSeperator = stateLabel?.let { ":: $it" } ?: ""
        return "[ $taskName ] $stateLabelWithSeperator"
    }

    private fun isCompletedState(state: Worker.State?) = when (state) {
        Worker.State.SUCCEEDED -> true
        Worker.State.FAILED -> true
        Worker.State.CANCELLED -> true
        else -> false
    }

    private fun triggerFadeOut() {
        val transition = SequentialTransition(
            PauseTransition(FADE_OUT_DELAY),
            FadeTransition(FADE_OUT_DURATION, this).apply {
                fromValue = 1.0
                toValue = 0.0
            }
        )
        transition.onFinished = EventHandler { removeFromParent() }

        transition.play()
    }

    private fun removeFromParent() {
        val parent = this.parent as Pane
        parent.children.remove(this)
    }

    companion object {
        private val FADE_OUT_DELAY = Duration.seconds(5.0)
        private val FADE_OUT_DURATION = Duration.millis(700.0)
    }
}
