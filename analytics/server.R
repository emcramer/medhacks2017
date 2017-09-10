
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
  
  db_url <- "https://docs.google.com/spreadsheets/d/1deQm1gLZMwScBEC9x_q4Slc4N6Yig2M7YIBQru9hleE/edit#gid=1854273697" # access DB
  db_ss <- gs_key(extract_key_from_url(db_url))
  history_df <- as.data.frame(gs_read(ss = db_ss, ws = 'history')) # get the tracker history
  history_df$Timestamp <- mdy_hms(history_df$Timestamp)
  
  # get patient data
  pt_df <- as.data.frame(gs_read(ss = db_ss, ws = 'pt_info'))
  pt_orders <- str_split(pt_df$OrderNum, ";")
  
  ids <- c()
  order_nums <- c()
  destinations <- c()
  pt_name <- c()
  for(i in 1:length(pt_orders)){
    for(j in 1:length(pt_orders[[i]])){
      order_nums <- c(order_nums, pt_orders[[i]][j])
      ids <- c(ids, i)
      destinations <- c(destinations, paste("R",i,sep=""))
      pt_name <- c(pt_name, pt_df[i, 4])
    }
  }
  pt_df2 <- data.frame(ID = ids, OrderNum = as.integer(order_nums), Destination = destinations)
  
  orders_df <- as.data.frame(gs_read(ss = db_ss, ws = 'order_info'))
  order_nfcs <- str_split(orders_df$UID, ";")
  
  nfcid_order_df <- full_join(orders_df[, -1], pt_df2[, -1])
  current_loc <- history_df[length(history_df$NFCID)-match(unique(history_df$NFCID),rev(history_df$NFCID))+1, ]
  
  status_df <- full_join(nfcid_order_df[, -2], current_loc)
  status_df$Location[is.na(status_df$Location)] <- "R0"
  for(i in 1:nrow(status_df)){
    status_df$status[i] <- if(status_df$Destination[i] == status_df$Location[i]) "arrived" else "in transit"
  }

  output$table <- renderDataTable({
    datatable(status_df[, -c(2:5, 7)]) %>% # populate table with unit dose travel history
      formatStyle(
        'status',
        backgroundColor = styleEqual(c('in transit', 'arrived'), c('gray', 'green'))
      )
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
