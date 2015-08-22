var gulp = require('gulp');
var fs = require('fs');
var path = require('path');
var concat = require('gulp-concat');
var jsonTransform = require('gulp-json-transform');
var merge = require('merge-stream');
var extReplace = require('gulp-ext-replace');
var rename = require("gulp-rename");
var clean = require('gulp-clean');
var del = require('del');
var dest = require('gulp-dest');
require('gulp-run-seq');

function getFolders(dir) {
    return fs.readdirSync(dir)
        .filter(function(file) {
            return fs.statSync(path.join(dir, file)).isDirectory();
        });
}

var buildDstPath = './build';
var elementsSrcPath = './elements';
var elementsDstPath = './build/elements';
var timestamp = new Date().getTime();

gulp.task('default', function() {
    console.log('There is no default task for gulp.');
});

gulp.task('build', ['elements-generateDeps'], function() {
    // Package elements.
    var elementNames = getFolders(elementsDstPath);

    var concatElements = elementNames
        .map(function(elementName) {
            return gulp
                .src(path.join(elementsDstPath, elementName, '/**/*html'))
                .pipe(concat(elementName + '.html'))
                .pipe(gulp.dest(elementsDstPath));
        });

    return merge(concatElements);
});

gulp.task('elements-generateDeps', ['elements-copySources'], function(end) {
    gulp
        .src(path.join(elementsDstPath, '/**/*.dep'))
        .pipe(jsonTransform(function(dep) {
            return dep
                .dependencies
                .map(function(dependency) {
                    return '<link rel="import" href="' + dependency + '?version=' + timestamp +'">';
                })
                .join('\n');
        }))
        .pipe(extReplace('dephtml'))
        .pipe(gulp.dest(elementsDstPath))
        .on('end', end);
});

gulp.task('elements-copySources', function(end) {
    console.log('Copy sources to build directory.');

    del.sync([path.join(buildDstPath, '/**')]);

    gulp
        .src(path.join(elementsSrcPath, '/**/*'))
        .pipe(gulp.dest(elementsDstPath))
        .on('end', end);
});