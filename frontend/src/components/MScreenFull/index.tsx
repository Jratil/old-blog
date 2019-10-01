import React, { useState } from 'react'
import { Icon, message } from 'antd'
import screenfull from 'screenfull'

const MScreenFull: React.FC<any> = props => {
    const [status, setStatus] = useState<boolean>(false)

    const handleFullscreen = () => {
        if (!screenfull.isEnabled) {
            message.warn('you browser can not work')
            return
        }
        screenfull.toggle()
        setStatus(!status)
    }

    return <Icon type={status ? 'fullscreen-exit' : 'fullscreen'} onClick={handleFullscreen} title="fullscreen"></Icon>
}

export default MScreenFull
