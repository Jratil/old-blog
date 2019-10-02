// console.log(666)
// const req = require.context('.', true, /.svg$/)
// console.log(req)
// export default req

// const requireAll = requireContext => requireContext.keys().map(requireContext)
// const req = require.context('.', false, /\.svg$/)
// const arr = requireAll(req)
// console.log(arr)

var cache = {}

function importAll(r) {
    // console.log(66666, r)
    r.keys().forEach(key => {
        const reg = /\.\/(.*?)\.svg$/
        const name = reg.exec(key)![1]
        cache[name] = r(key).default
    })
}

importAll(require.context('.', true, /\.svg$/))

export default cache

// console.log(cache)
