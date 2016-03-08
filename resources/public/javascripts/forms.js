$(document).ready(function() {

  var test_data = {"savings":800,"expenses":2000,"spending":1200};

  function buildResultsTable(data) {
    // builds a table from budget JSON returned from server
    // TODO: should probably check that the data is good

    var table = $("<table id=\"results-table\"></table>")
                .attr('class', 'pure-table pure-table-striped');
    table.append($("<thead>")
                 .append($("<tr>"))
                 .append($("<th>").text("Category"))
                 .append($("<th>").text("Amount ($USD)")));

    $.each(data, function(index, value) {
      table.append($("<tr>")
        .append($("<td>").text(index))
        .append($("<td>").text(value)));
    });

    $("#results-container").append(table);
  }

  // setup category information submit handler
  $("#budget-button").click(function(e) {
    e.preventDefault(); // don't submit the form
    $.post("compute-budget", $("#categories-form").serialize())
      .done(function(data) {
        // TODO: On response, this should create a new column with the budget  
        console.log("data: " + data);
        console.log("type: " + typeof(data));
        // create a results table
        buildResultsTable(JSON.parse(data));
      });
  });

  // add new categories handler
  $("#add-cat-button").click(function(e) {
    e.preventDefault(); // don't submit the form
    var cnum_regexp = /cat(\d+).*/;
    var last_cat_name = $("#categories-form input").last().attr("name");

    // extracts the first match group, the category number
    var last_cat_num = parseInt(cnum_regexp.exec(last_cat_name)[1]);
    var new_cat_num = last_cat_num + 1;

    // create new form input fields
    var new_cat_title = $("<input>")
                          .attr('name', 'cat' + new_cat_num + '-title')
                          .attr('type', 'text')
                          .attr('placeholder', 'Category Name');
    var new_cat_val = $("<input>")
                        .attr('name', 'cat' + new_cat_num + '-val')
                        .attr('type', 'number')
                        .attr('placeholder', 'Percentage');

    // put new input fields into fieldset
    var new_fieldset = $("<fieldset>");
    new_fieldset.append(new_cat_title);
    new_fieldset.append(new_cat_val);

    // add new input fields to document
    $("#budget-button").before(new_fieldset);
  });

});

