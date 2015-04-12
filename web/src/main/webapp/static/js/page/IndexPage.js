define(['jsx!component/QuestionList'], function(QuestionList) {
    function IndexPage(indexData) {
        this.questions = indexData.questions;
        this.init();
    }

    IndexPage.prototype.init = function() {
        React.render(
            <QuestionList questions={this.questions} />,
            document.getElementById('questions')
        );
    };

    return IndexPage;
});