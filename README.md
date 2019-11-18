# VanCloj

The main website of the Vancouver Clojure group.

## Usage

In order to watch the files and rebuild:

``` shell
yarn css-watch
```

``` shell
yarn html-watch
```

And then you can serve the website:

``` shell
cd public
python -m http.server
```

#### Nitty Gritty

The website now uses [Bulma](https://bulma.io/) along with our beloved Hiccup.

Additionally, the `public` dir is a git submodule containing the GitHub pages
repository.

## Deploy

There is a `deploy.sh` script in the repository root that will commit anything
in the `public` submodule __and__ also commit the parent. You need both when
working with submodules and given it is very easy to forget the script does it
for you.

## Unlicenced

This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or
distribute this software, either in source code form or as a compiled
binary, for any purpose, commercial or non-commercial, and by any
means.

In jurisdictions that recognize copyright laws, the author or authors
of this software dedicate any and all copyright interest in the
software to the public domain. We make this dedication for the benefit
of the public at large and to the detriment of our heirs and
successors. We intend this dedication to be an overt act of
relinquishment in perpetuity of all present and future rights to this
software under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

For more information, please refer to <http://unlicense.org/>
