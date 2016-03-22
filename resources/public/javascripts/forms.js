$(document).ready(function() {

  // Trigger hashchange event to make sure when navigating directly
  // to URL hashes, remote content is still fetched appropriately.
  $(window).trigger('hashchange');

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

  // utility function returns object containing fields 'month' and 'year'
  // representing the current month and year
  function getCurDate() {
    var date = new Date();
    // months are zero indexed, so we add one
    var month = date.getMonth() + 1;
    var year  = date.getFullYear();

    // pad month with '0' if less than 10
    if (parseInt(month) < 10) {
     month = '0' + month; // this also coerces to string
    }

    return {'month': month, 'year': year};
  }

  // returns the date specified by the URL
  function getURLDate() {
    var URL_fields = location.hash.substr(1).split('/');
    var month = URL_fields[1];
    var year  = URL_fields[3];

    return {'month': month, 'year': year};
  }

  function getBudgetInfo() {
    var URLDate = getURLDate();
    var month = URLDate.month;
    var year  = URLDate.year;

    console.log('month ' + month);
    console.log('year '  + year);

    // get budget for specified month/year from server
    var serverGetURL = '/month/' + month + '/year/' + year;

    // TODO: write function which updates the form controls with
    // the relevant budget data retrieved from backend.
    $.get(serverGetURL)
      .success(function(data) {
        var response = JSON.parse(data);
        if (response.categories) {
          var categories = JSON.parse(response.categories);
          console.log(categories);
        }
        else { console.log('object returned invalid'); }
        if (response.income) {
          console.log(response.income);
        }

        // build the actual table
        buildResultsTable(categories);

      })
      .error(function(e) {
        console.log('response returned an error');
        console.log(e);
      });
  }

  if (!location.hash) {
    // load default view: get budget page for current month / year
    var curDate = getCurDate();
    location.hash = '#month/' + curDate.month + '/year/' + curDate.year;
  }

  // set up hashchange listener
  $(window).on('hashchange', getBudgetInfo)

  // setup category information submit handler
  $("#budget-button").click(function(e) {
    e.preventDefault(); // don't submit the form
    var URLDate = getURLDate();
    var formData = $("#categories-form").serialize();
    formData = formData + '&month=' + URLDate.month;
    formData = formData + '&year=' + URLDate.year;
    console.log(formData);
    $.post("compute-budget", formData)
      .done(function(data) {
        // TODO: On response, this should create a new column with the budget  
        console.log("data: " + data);
        console.log("type: " + typeof(data));
        var curDate = getCurDate();

        location.hash = '#month/' + curDate.month + '/year/' + curDate.year;
        // manually trigger hashchange to make sure data is retrieved from server
        $(window).trigger('hashchange'); 

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

