<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <link rel="stylesheet" href="static/DPlayer.css">
    <head>
        <title>${param.movie}</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    </head>
    <body>

    <div id="dplayer"></div>

    <script type="application/javascript" src="static/hls.js"></script>
    <script type="application/javascript" src="static/DPlayer.js"></script>
<script>

    var dp = new DPlayer({
        container: document.getElementById('dplayer'),
        video: {
            url: 'm3u8/${param.movie}/index.m3u8',
            type: 'hls'
        }
    });

</script>
</body>

</html>
