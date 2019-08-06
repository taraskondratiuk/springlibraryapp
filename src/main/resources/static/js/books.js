$(function () {

    var $books = $('#books');

    var $name = $('#name');
    var $text = $('#text');
    var $daysToReturn = $('#daysToReturn');
    var $picUrl = $('#picUrl');
    var $addAuthor = $('#add-author');
    var $bookAttributes = $(".bookAttributes");

    var $filterAttributes = $(".filter-attributes");
    var $addAttributes = $(".add-attributes");
    var $attribute = $("#attribute");


    var $newAttribute = $('#new-attribute');

    var $filterAuthor = $('#filter-author');
    var $line = $('#line');


    var $pager = $('#pager');


    var optionTemplate = '' +
        '{{#.}}<option value="{{.}}">{{.}}</option>{{/.}}';

    var bookJSONTemplate =
        '{' +
            '"author": "{{author}}",' +
            '"name": "{{name}}",' +
            '"text": "{{text}}",' +
            '"daysToReturn": "{{daysToReturn}}",' +
            '"picUrl": "{{picUrl}}",' +
            '"attributes": [' +
            '{{#attributes}}' +
                '{' +
                '"id": "{{id}}",' +
                '"name": "{{name}}"' +
                '},' +
            '{{/attributes}}' +
            ']' +
        '}';

    var filterAttributeTemplate = '' +
        '<div class="checkbox">' +
            '<label>' +
                '<input class="filterBookAttribute" type="checkbox" data-id="{{id}}" name="filterBookAttribute" value="{{name}}">' +
                    '{{name}}' +
            '</label>' +
        '</div>';

    var addAttributeTemplate = '' +
        '<div class="checkbox">' +
            '<label>' +
                '<input class="addBookAttribute" type="checkbox" data-id="{{id}}" name="addBookAttribute" value="{{name}}">' +
                    '{{name}}' +
            '</label>' +
        '</div>';


    var deletedBookTemplate = '' +
        '<div class="deleted-book col-lg-4">'+
        '</div>';

    var bookTemplate = '' +
        '<div class="book_container col-lg-4">' +
            '<img src="{{picUrl}}" alt="book_pic">' +
            '<div class="book_info">' +
                '<h2 class="author_name">{{author}}</h2>' +
                '<p class="book_name">{{name}}</p>' +
                '<p class="book_attributes">{{#attributes}} {{name}} {{/attributes}}</p>' +
                '<p class="book_days_to_return translate" data-args="message.daystoreturn,{{daysToReturn}}">Days to return: {{daysToReturn}}</p>' +
            '</div>' +
            /*'<div class="book_button">' +
                '<a href="#"><p class="translate" data-args="message.take">Take book</p></a>' +
            '</div>' +*/
            '<div class="book_button">' +
                '<button class="my-button remove-book" data-id="{{id}}"><p class="translate" data-args="message.remove">Delete book</p></button>' +
            '</div>'+
        '</div>';


    var pagerTemplate = '' +
        '<div class="pages row">' +
        '<div class="col-lg-8"> ' +
            '<div class="page-item col-lg-3 disabled">' +
                '<button class="disabled translate" data-args="message.page">Page</button>' +
            '</div>' +
            '{{#.}}'+
            '<div class="page-item col-lg-1">\n' +
                '<button class="page-link page-button {{cssClass}}" url="{{href}}">{{page}}</button>' +
            '</div>' +
            '{{/.}}' +
        '</div>' +
        '</div>';


    $pager.on('click','.pageBtn', function () {
        var url = $(this).attr('url');

        $.ajax({
            type: 'GET',
            url: url,
            success: function (booksPage) {
                $books.empty();
                $pager.empty();
                var newUrl = url.substring(0, url.lastIndexOf('page='));
                placeBooks(booksPage, newUrl);

            }
        });
    });


    function addBook(book) {
        if (book.picUrl === '') {
            book.picUrl = '../static/sources/q-mark.png';
        }
        $books.append(Mustache.render(bookTemplate, book));
    }

    function addFilterAttribute(attribute) {
        $filterAttributes.append(Mustache.render(filterAttributeTemplate, attribute));
    }

    function addAddAttribute(attribute) {
        $addAttributes.append(Mustache.render(addAttributeTemplate, attribute));
    }

    function addOptions(arr) {
        $daysToReturn.append(Mustache.render(optionTemplate, arr));
    }

    function pager(page, cssClass, href) {
        this.page = page;
        this.cssClass = cssClass;
        this.href = href;
    }

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8089/lib/books',
        success: function (booksPage) {

            var url = 'http://localhost:8089/lib/books?';
            placeBooks(booksPage, url);

        }
    });
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8089/lib/attributes',
        success: function (attributes) {
            $.each(attributes, function (i, attribute) {
                addFilterAttribute(attribute);
                addAddAttribute(attribute);
            });
            var arr = [];
            for (var i = 5; i <= 30; i++) {
                arr.push(i);
            }
            addOptions(arr);


        }
    });

$('.add-book').on('click', function () {

        var attArr = [];
        var myUrl =  'http://localhost:8089/lib/attributes/bynames?names=';
        $.each($("input[name='addBookAttribute']:checked"), function(){
            myUrl +=($(this).val());
            myUrl+=',';
            attArr.push($(this).val());
        });

        var book = {
            name: $('#name').val(),
            text: $text.val(),
            daysToReturn: $daysToReturn.val(),
            picUrl: $picUrl.val(),
            author: $addAuthor.val(),
            attributes: attArr
        };


    var arr = $books.toArray();

    $books.append( Mustache.render(deletedBookTemplate));

console.log(2);



        $.ajax({
            type: 'POST',
            url: 'http://localhost:8089/lib/books',
            data: JSON.stringify(book),
            dataType: "json",
            contentType: "application/json",
            success: function (newBook) {
                if (newBook.picUrl === '') {
                    newBook.picUrl = '../static/sources/q-mark.png';
                }






                $div = document.getElementsByClassName('book_container');

                for (let i = 0; i < $div.length - 1; i++) {
                    $div[i+1] = $div[i];
                }

                var newEl = $(Mustache.render(bookTemplate, newBook));

                document.getElementById("books").replaceChild(newEl, $div[0]);

            },
            error : function () {
                alert('error')
            }
        });






    });

    $('#add-attribute').on('click', function () {

        var attribute = {
            name: $newAttribute.val()
        };

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8089/lib/attributes',
            data: attribute,
            success: function (newAttribute) {
                addFilterAttribute(newAttribute);
                addAddAttribute(newAttribute);
            },
            error : function () {
                alert('error')
            }
        });

    });

    $books.on('click','.remove-book'    , function () {

        var $div = $(this).closest('.book_container');

        $.ajax({
            type: 'DELETE',
            url: 'http://localhost:8089/lib/books/' + $(this).attr('data-id'),
            success: function () {
                $div.replaceWith(Mustache.render(deletedBookTemplate))
            },
            error :function () {
                alert('error')
            }
        });
    });

    $('#filter-button').on('click', function () {
        var myUrl =  'http://localhost:8089/lib/books?attributes=';

        $.each($("input[name='filterBookAttribute']:checked"), function(){
            myUrl +=($(this).val());
            myUrl+=',';
        });

        if ($line.val())
        myUrl += '&line=' +  $line.val();

        if($filterAuthor.val())
        myUrl += '&author=' + $filterAuthor.val();

        $.ajax({
            type: 'GET',
            url: myUrl,
            success: function (booksPage) {
                $('#books').empty();
                $('#pager').empty();
                placeBooks(booksPage, myUrl + '&')
            },
            error :function () {
                alert('error');
            }
        });
    });
    
    function placeBooks(booksPage, url) {
        $.each(booksPage.content, function (i, book) {
            addBook(book);
        });
        var arrPager = [];
        for (let i = 0; i < booksPage.totalPages; i++) {
            var cssClass = '';
            if (booksPage.number == i) {
                cssClass = 'active';
            } else {
                cssClass = 'pageBtn';
            }
            arrPager.push(new pager(i+1, cssClass, url + 'page=' + (i + 1)));

        }
        $pager.append(Mustache.render(pagerTemplate, arrPager));
        
    }

});


