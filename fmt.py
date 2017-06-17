#!/user/bin/env python
# -*- coding: utf-8 -*-
#
# Author: Xuemin Zhao (xmzhao1986@gmail.com)

import sys, json, random

if __name__ == '__main__':
    k = 50
    if len(sys.argv) > 1:
        k = int(sys.argv[1])

    for line in sys.stdin:
        line = line.strip()
        print json.dumps({'text':line, 'cluster':random.randint(0, k)},
                         ensure_ascii=False)
