package mobiledev.unb.ca.bubblegamelab

interface BubbleListener {
    fun onBubbleViewRemoved(bubbleView: BubbleView, wasPopped: Boolean = false)
}