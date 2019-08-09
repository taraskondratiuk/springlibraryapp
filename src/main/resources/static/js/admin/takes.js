$(function () {
    const $takes = $('#takes');

    const $pager = $('#pager');

    //filter form
    const $email = $('#email');
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
        '<h3 class="translate" data-args="message.emailphone,{{user.email}},{{user.countryCode}},{{user.phone}}">Reader email: {{user.email}}<br>Reader phone: {{user.phoneNumber}}</h3>' +
        '</div>' +
        '</div>';

    const nothingFoundTemplate = '' +
        '<div class="col-lg-16 nothing-found center">' +
        '<h3 class="translate" data-args="message.nothingfound">Nothing was found</h3>' +
        '</div>';


    $.ajax({
        type: 'GET',
        url: 'http://localhost:8089/lib/takes',
        success: function (takesPage) {

            let url = 'http://localhost:8089/lib/takes?';
            placeTakes(takesPage, url);

        }
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

        if ($email.val()) {
            requestUrl += '&email=' + $email.val();
        }
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

        var arrPager = [];
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
        $takes.append(Mustache.render(takeTemplate, take));
    }
});