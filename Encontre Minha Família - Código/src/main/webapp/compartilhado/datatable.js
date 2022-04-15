function dataTable(idTabela, dados, columns, columnDefs, tituloRelatorio) {

    var dataSrc = [];
    let componente = $(idTabela); 
      
    if($.fn.dataTable.isDataTable(idTabela)){    
        componente.DataTable().destroy().draw();
        componente.find("tr").remove();
        $("thead").empty();
    }

    let tabela = componente.DataTable({

        data: dados,
        columns: columns,
        columnDefs: columnDefs,
        dom: 'Bfrtip',
        order: [],
        autoWidth: true,
        //responsive: true,
        colReorder: {
            realtime: true
        }, 

        'initComplete': function() {
            var api = this.api();
        
            // Populate a dataset for autocomplete functionality
            // using data from first, second and third columns
            api.cells('tr', ['*']).every(function() {
                // Get cell data as plain text
                var data = $('<div>').html(this.data()).text();
                if (dataSrc.indexOf(data) === -1) {
                    dataSrc.push(data);
                }
            });
        
            // Sort dataset alphabetically
            dataSrc.sort();
        
            // Initialize Typeahead plug-in
            $('.dataTables_filter input[type="search"]', api.table().container()).typeahead
            ({
                    source: dataSrc,
                    afterSelect: function(value) {
                        api.search(value).draw();
                    }
            });
        },

        oLanguage: {
            oPaginate: {
                sFirst: "Primeiro",
                sLast:"Último"
            },
            sSearch: "Buscar na Tabela: ",
            sInfo: 'Mostrando de _START_ até _END_ de _TOTAL_ registro(s)',
            sEmptyTable: "Nenhum registro encontrado",
            sInfoEmpty: "Nenhum registro encontrado",
            sZeroRecords: "Nenhum registro encontrado",
            lengthMenu: 'Exibindo _MENU_ resultados por página'
        },
        scrollY:        '58vh',
        lengthChange:   false,
        pageLength:     500,
        deferRender:    true,
        scrollCollapse: true,
        pagingType:     "first_last_numbers"
    });
}