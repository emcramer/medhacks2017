
# This is the user-interface definition of a Shiny web application.
# You can find out more about building applications with Shiny here:
#
# http://shiny.rstudio.com
#

library(shiny)

shinyUI(fluidPage(

  # Application title
  titlePanel("Pharmascope Analytics"),

  # Sidebar with a slider input for number of bins
  sidebarLayout(
    sidebarPanel(
      div(img(src="pharmascope_logo.png", width = 200), style="text-align: center;")
    ),

    # Data panel
    mainPanel(
      tabsetPanel(
        tabPanel("Track Meds - Tabular", 
                 dataTableOutput("table")), # Show a searchable table to examine history
        tabPanel("Track Meds - Graph", 
                 plotlyOutput("chart")), # Show interactive chart
        tabPanel("Track Meds - Type", 
                 plotOutput("hist"))) # show histogram of medication types by NDC code
    )
  )
))
