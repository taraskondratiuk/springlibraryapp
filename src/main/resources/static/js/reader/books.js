$(function () {
    //books container
    const $books = $('#books');

    //filter form
    const $filterButton = $('#filter-button');
    const $filterAttributes = $(".filter-attributes");
    const $filterAuthor = $('#filter-author');
    const $filterLine = $('#line');

    //pager container
    const $pager = $('#pager');

    const nothingFoundTemplate = '' +
        '<div class="col-lg-16 nothing-found center">' +
        '<h3 class="translate" data-args="message.nothingfound">Nothing was found</h3>' +
        '</div>';


    const filterAttributeTemplate = '' +
        '<div class="checkbox">' +
        '<label>' +
        '<input class="filterBookAttribute" type="checkbox" data-id="{{id}}" name="filterBookAttribute" value="{{name}}">' +
        '{{name}}' +
        '</label>' +
        '</div>';


    const bookTemplate = '' +
        '<div class="book_container col-lg-4">' +
        '<img src="{{picUrl}}" alt="book_pic">' +
        '<div class="book_info">' +
        '<h2 class="author_name">{{author}}</h2>' +
        '<p class="book_name">{{name}}</p>' +
        '<p class="book_attributes">{{#attributes}} {{name}} {{/attributes}}</p>' +
        '<p class="book_days_to_return translate" data-args="message.daystoreturn,{{daysToReturn}}">Days to return: {{daysToReturn}}</p>' +
        '</div>' +
        '<div class="book-button">' +
        '<button class="take-book" data-id="{{id}}"><p class="translate" data-args="message.take">Take book</p></button>' +
        '</div>' +
        '</div>';

    const pagerTemplate = '' +
        '<div class="pages row {{^.}}hide{{/.}}">' +
        '' +
        '<div class="col-lg-8"> ' +
        '<div class="page-item col-lg-3 disabled">' +
        '<button class="disabled translate" data-args="message.page">Page</button>' +
        '</div>' +
        '{{#.}}' +
        '<div class="page-item col-lg-1">\n' +
        '<button class="page-link page-button {{cssClass}}" url="{{href}}">{{page}}</button>' +
        '</div>' +
        '{{/.}}' +
        '</div>' +
        '</div>';


    //get books
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8089/lib/books',
        success: function (booksPage) {

            let url = 'http://localhost:8089/lib/books?';
            placeBooks(booksPage, url);

        }
    });

    //get attributes
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8089/lib/attributes',
        success: function (attributes) {
            $.each(attributes, function (i, attribute) {
                addFilterAttribute(attribute);
            });
        }
    });


    $pager.on('click', '.pageBtn', function () {
        let url = $(this).attr('url');

        $.ajax({
            type: 'GET',
            url: url,
            success: function (booksPage) {
                $books.empty();
                $pager.empty();
                let newUrl = url.substring(0, url.lastIndexOf('page='));
                placeBooks(booksPage, newUrl);

            }
        });
    });


    $books.on('click', '.take-book', function () {

        let $div = $(this).closest('.book_container');

        $.ajax({
            type: 'PUT',
            url: 'http://localhost:8089/lib/books/take/' + $(this).attr('data-id'),
            success: function () {
                $div.html("");
                $div.attr("class", "book_container col-lg-4 invisible");
            },
            error: function () {
                alert('error')
            }
        });
    });

    $filterButton.on('click', function () {
        let filterUrl = 'http://localhost:8089/lib/books?attributes=';

        $.each($("input[name='filterBookAttribute']:checked"), function () {
            filterUrl += ($(this).val());
            filterUrl += ',';
        });

        if ($filterLine.val())
            filterUrl += '&line=' + $filterLine.val();

        if ($filterAuthor.val())
            filterUrl += '&author=' + $filterAuthor.val();

        $.ajax({
            type: 'GET',
            url: filterUrl,
            success: function (booksPage) {
                $books.empty();
                $pager.empty();
                placeBooks(booksPage, filterUrl + '&')
            },
            error: function () {
                alert('error');
            }
        });
    });

    function placeBooks(booksPage, url) {
        if (booksPage.content.length > 0) {
            $.each(booksPage.content, function (i, book) {
                addBook(book);
            });
        } else {
            $books.append(Mustache.render(nothingFoundTemplate));
        }

        let arrPager = [];
        for (let i = 0; i < booksPage.totalPages; i++) {
            let cssClass = '';
            if (booksPage.number === i) {
                cssClass = 'active';
            } else {
                cssClass = 'pageBtn';
            }
            arrPager.push(new Pager(i + 1, cssClass, url + 'page=' + (i + 1)));

        }

        $pager.append(Mustache.render(pagerTemplate, arrPager));

    }

    function addBook(book) {
        if (book.picUrl === '') {
            book.picUrl = '../static/sources/q-mark.png';
        }
        $books.append(Mustache.render(bookTemplate, book));
    }

    function addFilterAttribute(attribute) {
        $filterAttributes.append(Mustache.render(filterAttributeTemplate, attribute));
    }

    function Pager(page, cssClass, href) {
        this.page = page;
        this.cssClass = cssClass;
        this.href = href;
    }
});


