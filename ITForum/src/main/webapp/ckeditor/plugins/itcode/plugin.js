/**
 * 
 */
CKEDITOR.plugins.add('itcode', {
	requires : 'widget',

	icons : 'itcode',

	init : function(editor) {
		editor.widgets.add('itcode', {
			button: 'Запускаемый код',
			dialog: 'itcode',
			template : '<div class="itcode">'
					+ '<textarea class="itcode-source" readonly style="width:50%;"></textarea>'
					+ '<input type="hidden" class="itcode-lang" value=""/>'
					+ '<input type="button" class="itcode-run" value="Run" onclick="runCode(this)"/>'
					+ '</div>',
			editables : {
				source : {
					selector : 'textarea.itcode-source',
					allowedContent: 'textarea'
				}
			},
			allowedContent:
			        'div(!itcode); textarea(!itcode-source)[!readonly, !value, style]; input(!itcode-run)[onclick]; input(!itcode-lang)[!value]',

			requiredContent: 'div(itcode)',
			upcast: function( element ) {
				return element.name == 'div' && element.hasClass( 'itcode' );
			},
			
			init: function() {
				this.element.getChild(0).setHtml(this.data.source);
            	this.element.getChild(1).setValue(this.data.lang);
            	this.element.getChild(2).on('click', function(){
            		var to_compile = {
            			    "LanguageChoice": this.getPrevious().getValue(),
            			    "Program": this.getPrevious().getPrevious().getValue(),
            			    "Input": "",
            			    "CompilerArgs" : ""
            		    };

            		    $.ajax ({
            			        url: "https://rextester.com/rundotnet/api",
            			        type: "POST",
            			        data: to_compile
            			    }).done(function(data) {
            			        alert(data.Result);
            			    }).fail(function(data, err) {
            			        alert("fail " + JSON.stringify(data) + " " + JSON.stringify(err));
            		        });
            	});
            },

            data: function() {
            	this.element.getChild(0).setHtml(this.data.source);
            	this.element.getChild(1).setValue(this.data.lang);
            }
		});
		CKEDITOR.dialog.add( 'itcode', this.path + 'dialogs/itcode.js' );
	}
});