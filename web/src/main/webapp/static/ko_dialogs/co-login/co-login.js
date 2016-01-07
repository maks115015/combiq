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

        this.buttons = [];
    }

    return ViewModel;
});