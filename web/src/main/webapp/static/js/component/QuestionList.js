define(['jsx!component/QuestionBox'], function(QuestionBox) {
   return React.createClass({
       render: function() {
           return (
               <ul>
                   {this.props.questions.map(function(question, i) {
                       return (
                           <li><QuestionBox question={question} /></li>
                       );
                   }, this)}
               </ul>
           );
       }
   });
});