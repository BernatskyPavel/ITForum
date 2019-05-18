/**
 * 
 */
CKEDITOR.plugins.add('itcode', {
	requires: 'widget',

	icons: 'itcode',

	init: function (editor) {
		editor.widgets.add('itcode', {
			button: 'Запускаемый код',
			dialog: 'itcode',
			template: '<div class="itcode:" style="width: 50%;overflow: auto;max-height: 250px;">' +
				'<pre style="padding: 0;margin: 0;"><code class="itcode-source"></code></pre>' +
				'<input type="hidden" class="itcode-lang" value=""/>' +
				'<input id="but" type="button" value="Выполнить" onclick="runCode(this)"/>' +
				'<p></p></div>',
			editables: {
				source: {
					selector: 'textarea.itcode-source',
					allowedContent: 'textarea'
				}
			},
			allowedContent: 'div(!itcode); code(!itcode-source)[value, style, editable]; input(!itcode-run)[onclick]; input(!itcode-lang)[!value]; pre[style]',

			requiredContent: 'div(itcode)',
			upcast: function (element) {
				return element.name == 'div' && element.hasClass('itcode');
			},

			init: function () {
				//this.element.getChild(0).setHtml(this.data.source);
				var childs = this.element.getChild(0).getChildren().toArray();
				childs.forEach(element => {
					element.remove();
				});

				var prel = new CKEDITOR.dom.element('code');
				prel.setText(this.data.source);
				hljs.highlightBlock(prel.$);
				prel.appendTo(this.element.getChild(0));
				this.element.getChild(1).setValue(this.data.lang);
				var param = this.data.source;
				this.element.getChild(2).on('click', function () {
					var to_compile = {
						"LanguageChoice": this.element.getChild(1).getValue(),
						"Program": this.element.getChild(0).getChild(0).getText(),
						"Input": "",
						"CompilerArgs": ""
					};
					var result = this.element.getChild(3);
					$.ajax({
						url: "https://rextester.com/rundotnet/api",
						type: "POST",
						data: to_compile
					}).done(function (data) {
						if (data.Erorrs === "" || data.Errors == null)
							result.setText(data.Result);
						else {
							result.setText(data.Errors);
						}
					}).fail(function (data, err) {
						alert("fail " + JSON.stringify(data) + " " + JSON.stringify(err));
					});
				}, this);
			},

			data: function () {
				//this.element.getChild(0).setHtml(this.data.source);
				this.element.getChild(1).setValue(this.data.lang);
				let langs = "";
				switch (this.data.lang) {
					case '1':
						langs = "cs";
						break;
					case '2':
						langs = "vbnet";
						break;
					case '3':
						langs = "fs";
						break;
					case '4':
						langs = "java";
						break;
					case '5':
					case '24':
						langs = "python";
						break;
					case '6':
					case '7':
					case '26':
					case '27':
					case '28':
					case '29':
						langs = "cpp";
						break;
					case '8':
						langs = "php";
						break;
					case '9':
					case '15':
					case '25':
					case '43':
						langs = "";
						break;
					case '10':
						langs = "objc";
						break;
					case '11':
						langs = "haskell";
						break;
					case '12':
						langs = "ruby";
						break;
					case '13':
						langs = "perl";
						break;
					case '14':
						langs = "lua";
						break;
					case '16':
					case '33':
					case '34':
					case '35':
						langs = "sql";
						break;
					case '17':
					case '23':
						langs = "javascript";
						break;
					case '18':
						langs = "lisp";
						break;
					case '19':
						langs = "prolog";
						break;
					case '20':
						langs = "go";
						break;
					case '21':
						langs = "scala";
						break;
					case '22':
						langs = "scheme";
						break;
					case '30':
						langs = "d";
						break;
					case '31':
						langs = "r";
						break;
					case '32':
						langs = "tcl";
						break;
					case '37':
						langs = "swift";
						break;
					case '38':
						langs = "bash";
						break;
					case '39':
						langs = "ada";
						break;
					case '40':
						langs = "erlang";
						break;
					case '41':
						langs = "elixir";
						break;
					case '42':
						langs = "ocaml";
						break;
					case '44':
						langs = "bf";
						break;
					case '45':
						langs = "fortran";
						break;
					default:
						break;
				}

				var childs = this.element.getChild(0).getChildren().toArray();
				childs.forEach(element => {
					element.remove();
				});

				var prel = new CKEDITOR.dom.element('code');
				prel.setText(this.data.source);
				hljs.highlightBlock(prel.$);
				prel.appendTo(this.element.getChild(0));

			}
		});
		CKEDITOR.dialog.add('itcode', this.path + 'dialogs/itcode.js');
	}
});