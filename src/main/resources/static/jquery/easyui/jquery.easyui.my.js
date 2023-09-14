(jQuery.extend({
        // deprecated
        /**
         * 功能：打开一个窗口，窗口内容是iframe
         * 示例：$.iframeWin('/sys/perm/to-update?id=3', 'win___' + id, 450, 350, false);
         * @param src
         * @param winId
         * @param width
         * @param height
         * @param autoHeight true时，iframe自适应高度，window自适应居中
         */
        iframeWin: function (src, winId, width, height, autoHeight) {
            if(!src){
                throw new Error('src 不能为空')
            }
            if(!winId){
                throw new Error('winId 不能为空')
            }
            if(!width){
                throw new Error('width 不能为空')
            }
            if(!height){
                throw new Error('height 不能为空')
            }

            // iframe属性
            let $iframe = $('<iframe frameborder="0" width="100%"></iframe>');
            $iframe.attr({
                src: src,
                height: height
            }).html('loading...').on('load', function (){
                console.log('on load, iframe')
                if(!autoHeight){
                    return;
                }

                // iframe 高度 自适应，高度=body所有直接子元素高度的和
                let $children = $(this).contents().find('body').children();
                if(!$children && $children.length === 0){
                    return;
                }

                let height = 0;
                $children.each(function (index, ele) {
                    console.log(index, $(ele).height(), $(ele).css('display'), ele)
                    let display = $(ele).css('display');
                    if(display !== 'none'){
                        height = height + $(ele).height();
                    }
                })
                console.log('heightSum', height)
                $(this).height(height);

                // 窗口居中
                let bodyHeight = window.parent.$(document.body).height();
                let top = (bodyHeight - height) / 2;
                top = top < 0 ? 0 : top;

                window.parent.$('#' + winId).window({
                    top: top
                })
            })

            $('<div/>').window({
                id: winId,
                title: '数据更新',
                width: width,
                height: autoHeight ? 'auto' : height + 40,
                modal: true,
                minimizable: false,
                content: $iframe,
                'loadingMessage': 'loading...',
                onOpen: function () {
                    console.log('on open')
                },
                onClose: function () {
                    console.log('on close')
                    $(this).window('destroy');
                },
                onDestroy: function (){
                    console.log('on destroy')
                }
            });
        }
    })
)