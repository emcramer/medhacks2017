#
# This is a Shiny web application. You can run the application by clicking
# the 'Run App' button above.
#
# Find out more about building applications with Shiny here:
#
#    http://shiny.rstudio.com/
#

library(shiny)
library(googlesheets)
library(dplyr)
library(DT)
library(tidyverse)

PAGE_TITLE <- "Pharmascope"

# Define UI for application that draws a histogram
ui <- navbarPage(
  title = div(img(src = "pharmascope_logo.png", height = 20, width = 20), PAGE_TITLE), # Application title
  tabPanel("Track Meds", dataTableOutput("the_data")),
  tabPanel("Miscellaneous", "Placeholder")
)


# Define server logic required to draw a histogram
server <- function(input, output) {
  
    db_url <- "https://docs.google.com/spreadsheets/d/1Ke-UfjN8fhHfZkO4f-gwMgcsSeLbh17hREUghOgtE1o/edit?usp=sharing"
    db_ss <- gs_key(extract_key_from_url(db_url))
  
  # get the data
  pt_df <- as.data.frame(gs_read(ss = db_ss,ws = 'pt'))
  pt_orders <- str_split(pt_df[,2], ";")
  
  ids <- c()
  order_nums <- c()
  destinations <- c()
  pt_name <- c()
  for(i in 1:length(pt_orders)){
    for(j in 1:length(pt_orders[[i]])){
      order_nums <- c(order_nums, pt_orders[[i]][j])
      ids <- c(ids, i)
      destinations <- c(destinations, i)
      pt_name <- c(pt_name, pt_df[i, 4])
    }
  }
  pt_df2 <- data.frame(ID = ids, OrderNums = as.integer(order_nums), Destination = destinations)
  
  orders_df <- as.data.frame(gs_read(ss = db_ss, ws = 'orders'))
  order_nfcs <- str_split(orders_df[,3], ";")
  
  ids <- c()
  ndcs <- c()
  nfc_nums <- c()
  for(i in 1:length(order_nfcs)){
    for(j in 1:length(order_nfcs[[i]])){
      nfc_nums <- c(nfc_nums, order_nfcs[[i]][j])
      ids <- c(ids, i)
      ndcs <- c(ndcs, orders_df[i, 2])
    }
  }
  orders_df2 <- data.frame(OrderNums = ids, NDC = ndcs, NFC = as.integer(nfc_nums))
  
  nfcs_df <- as.data.frame(gs_read(ss = db_ss, ws = 'nfcs'))
  colnames(nfcs_df) <- c("NFC", "Location", "TimeStamp", "Status")
  
  nfcs_orders_df <- full_join(nfcs_df, orders_df2)
  nfcs_orders_pts_df <- full_join(nfcs_orders_df, pt_df2)
  nfcs_orders_pts_df[is.na(nfcs_orders_pts_df[,2]), 2] <- -1
  
  for(i in 1:nrow(nfcs_orders_pts_df)){
    if(nfcs_orders_pts_df[i, 8] == nfcs_orders_pts_df[i, 2]){
      nfcs_orders_pts_df[i, 4] = "arrived"
    } else{
      nfcs_orders_pts_df[i, 4] = "in transit"
    }
  }
   
   output$the_data <- renderDataTable({
     rendered_table <- data.frame(
       'Patient ID' = nfcs_orders_pts_df[, 7],
       'Order Number' = nfcs_orders_pts_df[, 5],
       'NFC Tag Number' = nfcs_orders_pts_df[, 1],
       'NDC' = nfcs_orders_pts_df[, 5],
       'Location' = nfcs_orders_pts_df[, 2],
       'Time Stamp' = nfcs_orders_pts_df[, 3],
       'Status' = nfcs_orders_pts_df[, 4]
     )
     
     datatable(rendered_table) %>% formatStyle(
       'Status',
       backgroundColor = styleEqual(c('in transit', 'arrived'), c('gray', 'green'))
     )
     
   })

}

# Run the application 
shinyApp(ui = ui, server = server)

