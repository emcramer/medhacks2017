
# This is the server logic for a Shiny web application.
# You can find out more about building applications with Shiny here:
#
# http://shiny.rstudio.com
#

library(shiny)
library(tidyverse)
library(stringr)
library(dplyr)
library(DT)
library(googlesheets)
library(ggplot2)
library(plotly)
library(ggthemes)

shinyServer(function(input, output) {
  
  db_url <- "https://docs.google.com/spreadsheets/d/1Ke-UfjN8fhHfZkO4f-gwMgcsSeLbh17hREUghOgtE1o/edit?usp=sharing"
  db_ss <- gs_key(extract_key_from_url(db_url))
  history_df <- as.data.frame(gs_read(ss = db_ss, ws = 'history'))
  
  # dummy data ! REMOVE!
  set.seed(123)
  history_df <- data.frame(
    ID = 1:15,
    NDC = replicate(15, sample(1:10))[1, ],
    NFCID = paste("T", 1:15, sep = ""),
    Location = replicate(15, sample(1:4))[1, ],
    TimeStamp = seq(ISOdate(2017,09,09), by = "min", length.out = 15)
  )

  output$table <- renderDataTable({
    datatable(history_df) # populate table with unit dose travel history
  })
  
  output$chart <- renderPlotly({
    p <- ggplot(data = history_df) + geom_point(aes(x = TimeStamp, y = Location, color = NDC, size = 3))
    ggplotly(p)
  })
  
  output$hist <- renderPlot({
    pie_df <- as.data.frame(table(history_df[, 2]))
    ggplot(data = pie_df, aes(x="", y = Var1, fill = Freq)) + coord_polar("y", start=0)
  })

})
