const handler = require(process.argv[2]).handler

handler({path: 'npm/@spatial/turf/1.0.5/lol.module.sha1'})
    .then((res) => console.log(res))
