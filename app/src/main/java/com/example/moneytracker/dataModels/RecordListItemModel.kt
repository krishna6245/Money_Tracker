package com.example.moneytracker.dataModels

data class RecordListItemModel(
    var recordList: MutableList<RecordItemModel> = mutableListOf(),
)