define(['knockout'], function(ko) {
      function ViewModel(params){
          this.width = 720;
          this.align = 'top';
          this.buttons = [];
          this.sticked = true;
          this.posting = ko.wrap(false);
          this.title=ko.wrap(params.title);
          this.body=ko.wrap(params.body);
          this.tags=ko.observableArray();
          this.tag=ko.wrap('Выберите теги');
          this.lvl=ko.wrap(params.lvl);
          this.avaibleTag=ko.observableArray();
          var self=this;
          $.getJSON('/questions/tags', function(result) {
          for (var i = 0; i < result.length; i++) {
               self.avaibleTag.push({name:result[i]});
          }});
          this.removePerson = function() {
                  self.tags.remove(this);
              };
      }
      ViewModel.prototype.deletetag=function(tag){
          this.tags.slice(tag,1);
      }

      ViewModel.prototype.addTag=function(){
          if(this.tag()!='Выберите теги' && this.tags.indexOf(this.tag())==-1){
                  this.tags.push(this.tag());}}
      ViewModel.prototype.send=function(){
      var self=this;
      $.ajax({
                    url: 'questions/new',
                    data: JSON.stringify({title: self.title(),body: self.body() , level: self.lvl(), tags: self.tags()}),
                    contentType: 'application/json',
                    method: 'POST',
                    success: function(result) {self.title(result); }
          });
      }
      return ViewModel;
});