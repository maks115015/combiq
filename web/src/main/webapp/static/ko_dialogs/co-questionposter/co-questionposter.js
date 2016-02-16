define(['knockout','ajax'], function(ko,ajax) {
      function ViewModel(params){
          this.width = 720;
          this.align = 'top';
          this.buttons = [];
          this.sticked = true;
          this.posting = ko.wrap(false);
          this.title=ko.wrap(params.title);
          this.body=ko.wrap(params.body);
          this.tags=ko.observableArray();
          this.tag=ko.wrap('');
          this.istag=ko.wrap(false);
          this.lvl=ko.wrap(params.lvl);
          this.avaibleTag=ko.observableArray();
          var self=this;
          $.getJSON('/questions/tags', function(result) {
          for (var i = 0; i < result.length; i++) {
               self.avaibleTag.push({name:result[i]});
          }});
          this.removeTag = function() {
                  self.tags.remove(this);
              };
      }
      ViewModel.prototype.addTag=function(){
          if(this.istag() && this.tags.indexOf(this.tag())==-1){
                  this.tags.push(this.tag());}
                  this.istag(true);}

      ViewModel.prototype.send=function(){
      taglist=[];
      var size= this.tags().length;
      for(var i=0;i<size;i++){
      taglist.push(this.tags()[i].name);
      };
      var json={title: this.title(), body: {markdown:this.body()},level:this.lvl(), tags: taglist};
      ajax.rest('POST', '/questions/new', json).done(function() {alert('OK!');});
      }
      return ViewModel;
});