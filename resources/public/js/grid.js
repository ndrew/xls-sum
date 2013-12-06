function fileClicked(file) {
    //var file = $(e.target).text();
    $.ajax({
        url: "/data",
        data: {
            path: $("[name=path]").val(),
            file: file
        }
    })
    .done(function(response) {
        var sheet = Object.keys(response.data.data)[0];
        var meta = response.data.meta[sheet];

        var data = response.data.data[sheet]; 

    for (var i = meta["col-widths"].length - 1; i >= 0; i--) {
        meta["col-widths"][i] = meta["col-widths"][i] / 40;
    };
     

    $("#grid").handsontable({
        data: data,
        rowHeaders: true,
        colHeaders: true,
        colWidths: meta["col-widths"],
        scrollH: 'auto',
        scrollV: 'auto',
        stretchH: 'hybrid',
        startRows: 10,
        startCols: 10,
        afterSelection: function(changes, source) {
            console.log(arguments);
        },
        cells: function (row, col, prop) {
            var cellProperties = {};
            cellProperties.readOnly = true;                   
            
            for (var i = meta.merges.length - 1; i >= 0; i--) {
                var box = meta.merges[i];
                if (col == box[0] && row == box[1]) {
                    cellProperties.renderer = function(instance, td, row, col, prop, value, cellProperties) {
                        Handsontable.TextCell.renderer.apply(this, arguments);
                        td.setAttribute("colSpan", box[2]-box[0]);
                        td.setAttribute("rowSpan", box[3]-box[1]);
                    };
                }
            }    
            return cellProperties;
        }
    });


      //  alert( "sucess" );
    })
    .fail(function() {
        alert( "error" );
    });
}



$.ajax("/init" ).done(
    function(response) {
        $("[name=path]").val(response.path);

        var list = $("#main aside ul");
        list.empty();
        $.each(response.files, function(i) {
            var li = $('<li/>').appendTo(list);

            $('<label/>')
                .text(response.files[i])
                .appendTo(li)
                .click(function(){
                    //checkbox.prop("checked", !checkbox.prop("checked"));
                    //fileClicked($(this).text());
                });
            
            /*var checkbox = $('<input/>')
                .attr('type', 'checkbox')
                .click(function() {
                    fileClicked(response.files[i]);
                })
                .appendTo(li);

            */


            /*
            var aaa = $('<a/>')
                .attr("href","#")
                //.addClass('ui-all')
                .text(response.files[i])
                .click(fileClicked)
                .appendTo(li); */
        });
  })
  .fail(function() {
    alert( "error" );
  })
  .always(function() {
    //alert( "complete" );
  });


