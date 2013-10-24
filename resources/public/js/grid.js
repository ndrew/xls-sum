function fileClicked(e) {
    var file = $(e.target).text();
    $.ajax({
        url: "/data",
        data: {
            path: $("[name=path]").val(),
            file: file
        }
    })
    .done(function(response) {
        alert( "sucess" );
    })
    .fail(function() {
        alert( "error" );
    });
}

$.ajax("/init" )
  .done(function(response) {
    $("[name=path]").val(response.path);
    



    var list = $("#main aside ul");
    list.empty();
    $.each(response.files, function(i) {
    var li = $('<li/>')
        //.addClass('ui-menu-item')
        //.attr('role', 'menuitem')
        .appendTo(list);
    var aaa = $('<a/>')
        .attr("href","#")
        //.addClass('ui-all')
        .text(response.files[i])
        .click(fileClicked)
        .appendTo(li);
    });
    //console.log(response);


    //alert("success" );
  })
  .fail(function() {
    alert( "error" );
  })
  .always(function() {
    //alert( "complete" );
  });

/*  var data = [
    ["", "Kia", "Nissan", "Toyota", "Honda"],
    ["2008", 10, 11, 12, 13],
    ["2009", 20, 11, 14, 13],
    ["2010", 30, 15, 12, 13]
  ];
  $("#grid").handsontable({
    data: data,
    startRows: 6,
    startCols: 8
  });

*/