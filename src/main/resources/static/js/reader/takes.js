$(function () {
    const $takes = $('#takes');

    const $pager = $('#pager');

    //filter form
    const $returned = $('#returned');

    const pagerTemplate = '' +
        '<div class="pages row {{^.}}hide{{/.}}">' +
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

    const takeTemplate = '' +
        '<div class="take-container {{colorClass}} col-lg-16">' +
        '<div class="book-take-pic col-lg-6">' +
        '<img src="{{book.picUrl}}" alt="book_pic">' +
        '</div>' +
        '<div class="col-lg-10 take-info">' +
        '<h3 class="translate" data-args="message.nameauthor,{{book.name}},{{book.author}}">Book name: {{book.name}}<br>Author: {{book.author}}</h3>' +
        '<h3 class="translate" data-args="message.takedead,{{takeDate}},{{returnDate}}">Take date: {{takeDate}}<br>Return deadline: {{returnDeadline}}</h3>' +
        '{{#isReturned}}' +
        '<h3 class="translate" data-args="messagae.returndate,{{returnDeadline}}">Return date: {{returnDate}}</h3>' +
        '{{/isReturned}}' +
        '{{^isReturned}}' +
        '<button class="return-book" data-id="{{id}}">return</button>' +
        '{{/isReturned}}' +
        '</div>' +
        '</div>';

    const nothingFoundTemplate = '' +
        '<div class="col-lg-16 nothing-found center">' +
        '<h3 class="translate" data-args="message.nothingfound">Nothing was found</h3>' +
        '</div>';


    //get takes
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8089/lib/takes/my',
        success: function (takesPage) {

            let url = 'http://localhost:8089/lib/takes/my?';
            placeTakes(takesPage, url);

        }
    });

    $takes.on('click', '.return-book', function () {
        let $oldTake = $(this).closest('.take-container');

        $.ajax({
            type: 'PUT',
            url: 'http://localhost:8089/lib/takes/return/' + $(this).attr('data-id'),
            success: function (returnedTake) {
                let index = $takes.index($oldTake);
                $oldTake.remove();
                $takes.eq(index).before(Mustache.render(takeTemplate, determineColorClass(returnedTake)));
            },
            error: function () {
                alert('error')
            }
        });

    });


    $pager.on('click', '.pageBtn', function () {
        let url = $(this).attr('url');

        $.ajax({
            type: 'GET',
            url: url,
            success: function (booksPage) {
                $takes.empty();
                $pager.empty();
                let newUrl = url.substring(0, url.lastIndexOf('page='));
                placeTakes(booksPage, newUrl);

            }
        });
    });

    $('#filter-button').on('click', function () {
        let requestUrl = 'http://localhost:8089/lib/takes?=';

        requestUrl += '&returned=' + $returned.val();

        $.ajax({
            type: 'GET',
            url: requestUrl,
            success: function (takesPage) {
                $takes.empty();
                $pager.empty();
                placeTakes(takesPage, requestUrl + '&')
            },
            error: function () {
                alert('error');
            }
        });
    });

    function placeTakes(takesPage, url) {
        if (takesPage.content.length !== 0) {
            $.each(takesPage.content, function (i, take) {
                addTake(take);
            });
        } else {
            $takes.append(Mustache.render(nothingFoundTemplate));
        }

        let arrPager = [];
        for (let i = 0; i < takesPage.totalPages; i++) {
            let cssClass = '';
            if (takesPage.number === i) {
                cssClass = 'active';
            } else {
                cssClass = 'pageBtn';
            }
            arrPager.push(new pager(i + 1, cssClass, url + 'page=' + (i + 1)));

        }
        $pager.append(Mustache.render(pagerTemplate, arrPager));

    }

    function pager(page, cssClass, href) {
        this.page = page;
        this.cssClass = cssClass;
        this.href = href;
    }

    function addTake(take) {
        $takes.append(Mustache.render(takeTemplate, determineColorClass(take)));
    }


    function determineColorClass(take) {
        if (take.book.picUrl === '') {
            take.book.picUrl = '../static/sources/q-mark.png';
        }
        if (take.isReturned) {
            if (take.returnDate > take.returnDeadline) {
                take.colorClass = "returned-after-deadline"
            } else {
                take.colorClass = "returned"
            }
        } else {
            let d = new Date().toISOString().substring(0, 10);
            if (take.returnDeadline < d) {
                take.colorClass = "not-returned-deadline";
            } else {
                take.colorClass = "not-returned"
            }

        }

        return take;
    }
});