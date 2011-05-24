#!/usr/bin/env python
# -*- coding: utf-8 -*-


class Collider(object):
    """ generated source for Collider

    """

    @classmethod
    @overloaded
    def collidesLevel(cls, m):
        if m.getLevel().collides(AnimCreator.getCurrentFrameRect(m)):
            return True
        return False

    @classmethod
    @collidesLevel.register(type, Projectile)
    def collidesLevel_0(cls, p):
        if p.getLevel().collides(p.getShape()):
            return True
        return False


