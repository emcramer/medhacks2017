
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
library(lubridate)

shinyServer(function(input, output) {
  
  db_url <- "https://docs.google.com/spreadsheets/d/1deQm1gLZMwScBEC9x_q4Slc4N6Yig2M7YIBQru9hleE/edit#gid=1854273697"
  db_ss <- gs_key(extract_key_from_url(db_url))
  history_df <- as.data.frame(gs_read(ss = db_ss, ws = 'history'))
  history_df[, 1] <- mdy_hms(history_df[, 1])
  
  # dummy data ! REMOVE!
  # set.seed(123)
  # history_df <- data.frame(
  #   ID = 1:15,
  #   NDC = replicate(15, sample(1:10))[1, ],
  #   NFCID = paste("T", 1:15, sep = ""),
  #   Location = replicate(15, sample(1:4))[1, ],
  #   TimeStamp = seq(ISOdate(2017,09,09), by = "min", length.out = 15)
  # )

  output$table <- renderDataTable({
    datatable(history_df) # populate table with unit dose travel history
  })
  
  output$chart <- renderPlotly({
    p <- ggplot(data = history_df) + geom_point(aes(x = Timestamp, y = Location, color = NDC, size = 3))
    ggplotly(p)
  })
  
  output$hist <- renderPlot({
    pie_df <- as.data.frame(table(history_df[, 3]))
    ggplot(data = pie_df) + geom_histogram(aes(x = Var1, y = Freq, fill = Freq), stat = "identity", bins = nrow(pie_df)) + xlab("NDC Code") + ylab("Frequency") + ggtitle("Frequency of Medications Dispensed")
  })

})
