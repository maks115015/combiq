define(['knockout','ajax'], function(ko,ajax) {

    function ViewModel(params){
        this.width = 720;
        this.align = 'top';
        this.buttons = [];
        this.sticked = true;
        this.posting = ko.wrap(false);
        this.tag=ko.wrap('');
        this.istag=ko.wrap(false);
        this.id=ko.wrap(params.id);
        this.lvl=ko.wrap(params.level);
        this.title=ko.wrap(params.title);
        this.body=ko.wrap(params.body);
        if (this.id()!=''){
            this.tags=ko.observableArray(params.tags.map(function(tagname,index,array) {
                    return {name: tagname};
                }));
        } else{
            this.tags=ko.observableArray();
        };
        this.avaibleTag=ko.observableArray();
        var self=this;
        $.getJSON('/questions/tags', function(result) {
            for (var i = 0; i < result.length; i++) {
                self.avaibleTag.push({name:result[i]});
            };
        });
        this.removeTag = function() {
            self.tags.remove(this);
        };
    };

    ViewModel.prototype.addTag=function(){
        var self=this;
        if(this.istag() && this.tags.indexOf(this.tag())==-1){
            this.tags.push(this.tag());
        };
        this.istag(true);
    }

    ViewModel.prototype.send=function(){
        var self=this;
        taglist = this.tags()
            .map(function(tag,index,array) {return tag.name;});
        var json={title: this.title(), body: {markdown:this.body()},level:this.lvl(), tags: taglist};
        if(this.id()==''){
            ajax.rest('POST', '/questions/new', json)
                .done(function() {
                    self.posting(false);
                    location.reload();
                });
            } else{
            json.id=this.id();
            ajax.rest('POST', '/questions/'+this.id(), json)
                .done(function() {
                    self.posting(false);
                    location.reload();
                });
        };
    };

    return ViewModel;
});