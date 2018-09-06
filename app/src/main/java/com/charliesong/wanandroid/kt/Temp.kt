package com.charliesong.wanandroid.kt

sealed class  ListState

class Initialized:ListState()

class Empty:ListState()

class EndOfBottom:ListState()