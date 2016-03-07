define([], function() {

    function ViewModel() {
        this.githubUrl = 'https://github.com/login/oauth/authorize?client_id='
            + window.co.githubClientId
            + '&scope=user&state='
            + window.co.githubClientState
            + ':' + encodeURIComponent(location.pathname + (location.search || ''));

        this.vkUrl = 'https://oauth.vk.com/authorize?client_id='
            + window.co.vkClientId
            + '&display=page&response_type=code&redirect_uri='
            + encodeURIComponent(window.co.vkCallbackUrl)
            + '&state='
            + window.co.vkClientState
            + ':' + encodeURIComponent(location.pathname + (location.search || ''));

        this.fbUrl = 'https://www.facebook.com/dialog/oauth?client_id='
            + window.co.facebookClientId
            + '&scope=public_profile&redirect_uri='
            + encodeURIComponent(window.co.facebookCallbackUrl)
            + '&state='
            + window.co.facebookClientState
            + ':' + encodeURIComponent(location.pathname + (location.search || ''));

        this.stackexchangeUrl = 'https://stackexchange.com/oauth?client_id='
            + window.co.stackexchangeClientId
            + '&scope=no_expiry&redirect_uri='
            + encodeURIComponent(window.co.stackexchangeCallbackUrl)
            + '&state='
            + window.co.stackexchangeClientState
            + ':' + encodeURIComponent(location.pathname + (location.search || ''));

        this.width = 360;
        this.align = 'top';
        this.buttons = [];
    }

    return ViewModel;
});