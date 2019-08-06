var set_locale_to = function(locale) {
    if (locale)
        $.i18n().locale = locale;

    $('.translate').each(function() {
        var args = [], $this = $(this);
        if ($this.data('args'))
            args = $this.data('args').split(',');
        $this.html( $.i18n.apply(null, args) );
    });
};

jQuery(function() {

    $.i18n().load( {
        'en': 'http://localhost:8089/lib/locale/en',
        'ua': 'http://localhost:8089/lib/locale/ua'

    } ).done(function() {

        set_locale_to(url('?locale'));

        History.Adapter.bind(window, 'statechange', function(){
            set_locale_to(url('?locale'));
        });
//alert('locale');
        $('.switch-locale').on('click', 'a', function(e) {
            e.preventDefault();
            History.pushState(null, null, "?locale=" + $(this).data('locale'));
        });
    });
});