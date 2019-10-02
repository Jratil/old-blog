import React from 'react'
import { Icon } from 'antd'
import icons from '@/icons/index.ts'

const MIcon = ({ type, style = {}, ...props }) => {
    const isCustom = Object.keys(icons).includes(type)
    const tempStyle = {
        color: 'rgba(0,0,0,.25)',
        ...style
    }
    return <Icon type={isCustom || type} style={tempStyle} component={isCustom && icons[type]} {...props} />
}

export default MIcon
