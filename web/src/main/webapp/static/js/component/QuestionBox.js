define([], function() {
    return React.createClass({
        render: function() {
            return (
                <div>{this.props.question.title}</div>
            )
        }
    });
});